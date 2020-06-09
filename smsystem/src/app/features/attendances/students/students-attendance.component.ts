import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent, MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { NbDialogService } from '@nebular/theme';
import { MatTableDataSource} from '@angular/material/table';
import { DeleteModalComponent } from 'app/shared';

import { ClassAttendenceCreateComponent } from './class-attendance-create/class-attendance-create.component';

import { MeetingTime, ClassAttendance, ResponseWrapper } from 'app/models';
import { MeetingTimeService, ClassAttendanceService } from 'app/services';
import { QueryParam, APP_ACTION_TYPE} from 'app/utils';

@Component({
  selector: 'app-students',
  templateUrl: './students-attendance.component.html',
  styleUrls: ['./students-attendance.component.scss'],
})
export class StudentsAttendanceComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  baseURL: string = '/dashboard/admissions/attendances/class-attendances';
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'mClass',  'academicYearTerm', 'academicYear', 'action'];
  dataSource: MatTableDataSource<ClassAttendance> = new MatTableDataSource<ClassAttendance>();
  classAttendances: ClassAttendance[];

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
    private classAttendanceService: ClassAttendanceService,
    private dialogService: NbDialogService,
    private router: Router,
    ) {
  }

  ngOnInit() {
    this.loadClassAttendances();
  }


  open() {
    this.dialogService.open(ClassAttendenceCreateComponent, {
      context: {
        title: 'Add New Class Attendance',
        action: APP_ACTION_TYPE.create,
      }
    }).onClose.subscribe(_data => {

    });
  }


  loadClassAttendances() {
    this.isLoading = true;
    this.classAttendanceService.loadClassAttendancesPaginated(this.param)
    .subscribe((result: ResponseWrapper) => {
      const resp = result;
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.classAttendances = data.classAttendances;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<ClassAttendance>(data.classAttendances);
        }
    }, error => {
      this.isLoading = false;
    });
  }

  edit(classAttendance: ClassAttendance) {
    this.dialogService.open(ClassAttendenceCreateComponent, {
      context: {
        title: 'Add New Class Attendance',
        action: APP_ACTION_TYPE.edit,
        classAttendance: classAttendance,
      },
    }).onClose.subscribe(_data => {
      this.loadClassAttendances();
    });
  }

  delete(classAttendance: ClassAttendance) {
    this.dialogService.open(DeleteModalComponent, {
      context: {
        title: 'Delete Class',
        action: 'delete',
        id: classAttendance.id.toString(),
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.loadClassAttendances();
      }
    });
  }

  view(classAttendance: ClassAttendance) {
    this.router.navigate([this.baseURL + '/' + classAttendance.id ]);
  }

  onPageChange(pageEvent: PageEvent) {
      this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
      this.param.pageSize = pageEvent.pageSize;
      this.loadClassAttendances();
  }

}
