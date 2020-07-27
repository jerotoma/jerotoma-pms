import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { PageEvent, MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { NbDialogService } from '@nebular/theme';
import { MatTableDataSource} from '@angular/material/table';
import { DeleteModalComponent } from 'app/shared';

import { AttendanceStatusesCreateComponent } from './create/attendance-statuses-create.component';

import {AttendanceStatus, ResponseWrapper, USER_ROLE } from 'app/models';
import { AttendanceStatusService, SecurityClearanceService } from 'app/services';
import { QueryParam, APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-attendance-statuses',
  templateUrl: './attendance-statuses.component.html',
  styleUrls: ['./attendance-statuses.component.scss'],
})
export class AttendanceStatusesComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  private userRole: USER_ROLE;
  private userRoles: USER_ROLE[];
  baseURL: string = '/dashboard/attendances/students';
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'description',  'createdOn', 'action'];
  dataSource: MatTableDataSource<AttendanceStatus> = new MatTableDataSource<AttendanceStatus>();
  attendanceStatuses: AttendanceStatus[];

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  constructor(
    private attendanceStatusService: AttendanceStatusService,
    private securityClearanceService: SecurityClearanceService,
    private dialogService: NbDialogService,
    private router: Router,
    ) {
  }

  ngOnInit() {
    this.loadCurrentUser();
    this.loadAttendanceStatuses();
  }

  loadCurrentUser() {
    this.securityClearanceService.loadCurrentUser();
  }

  get hasResult() {
    return this.securityClearanceService.hasResult;
  }

  get isAdminsOrExecutive(): boolean {
    return this.securityClearanceService.isAdminsOrExecutive;
  }


  open() {
    this.dialogService.open(AttendanceStatusesCreateComponent, {
      context: {
        title: 'Add New Attendance Status',
        action: APP_ACTION_TYPE.create,
      },
    }).onClose.subscribe(_data => {
      this.loadAttendanceStatuses();
    });
  }


  loadAttendanceStatuses() {
    this.isLoading = true;
    this.attendanceStatusService.loadAttendancesPaginated(this.param)
    .subscribe((result: ResponseWrapper) => {
      const resp = result;
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.attendanceStatuses = data.attendanceStatuses;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<AttendanceStatus>(data.attendanceStatuses);
        }
    }, error => {
      this.isLoading = false;
    });
  }

  edit(attendanceStatus: AttendanceStatus) {
    this.dialogService.open(AttendanceStatusesCreateComponent, {
      context: {
        title: 'Edit Attendance Status',
        action: APP_ACTION_TYPE.edit,
        attendanceStatus: attendanceStatus,
      },
    }).onClose.subscribe(_data => {
      this.loadAttendanceStatuses();
    });
  }

  delete(attendanceStatus: AttendanceStatus) {
    this.dialogService.open(DeleteModalComponent, {
      context: {
        title: 'Delete Attendance Status',
        action: 'delete',
        id: attendanceStatus.id.toString(),
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.deleteStatus(result.id);
      }
    });
  }
  deleteStatus(statusID: number) {
    this.attendanceStatusService.deleteStatus(statusID).subscribe((success: boolean) => {
      if (success) {
        this.loadAttendanceStatuses();
      }
    });
  }


  onPageChange(pageEvent: PageEvent) {
      this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
      this.param.pageSize = pageEvent.pageSize;
      this.loadAttendanceStatuses();
  }


}
