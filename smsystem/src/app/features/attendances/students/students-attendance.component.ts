import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { PageEvent, MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { NbDialogService } from '@nebular/theme';
import { MatTableDataSource} from '@angular/material/table';
import { DeleteModalComponent } from 'app/shared';

import { ClassAttendenceCreateComponent } from '../classes/class-attendance-create/class-attendance-create.component';

import { MeetingTime, ClassAttendance, ResponseWrapper } from 'app/models';
import { MeetingTimeService, ClassAttendanceService } from 'app/services';
import { QueryParam, APP_ACTION_TYPE} from 'app/utils';

@Component({
  selector: 'app-students-attendances',
  templateUrl: './students-attendance.component.html',
  styleUrls: ['./students-attendance.component.scss'],
})
export class StudentsAttendanceComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  baseURL: string = '/dashboard/admissions/attendances/classes';
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  classId: number;
  displayedColumns: string[] = ['id', 'fullName', 'mClass',  'academicYearTerm', 'academicYear', 'attendanceDate', 'action'];
  dataSource: MatTableDataSource<ClassAttendance> = new MatTableDataSource<ClassAttendance>();
  classAttendance: ClassAttendance;

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
    private route: ActivatedRoute,
    private router: Router,
    ) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.classId = params['classId'];
      if (this.classId) {
        this.loadClassAttendances(this.classId);
      }
    });
  }

  open() {
    this.dialogService.open(ClassAttendenceCreateComponent, {
      context: {
        title: 'Add New Class Attendance',
        action: APP_ACTION_TYPE.create,
      },
    }).onClose.subscribe(_data => {

    });
  }

  loadClassAttendances(classId: number) {
    this.isLoading = true;
    this.classAttendanceService.getClassAttendance(classId)
    .subscribe((classAttendance: ClassAttendance) => {
        this.isLoading = false;
        if (classAttendance) {
          this.classAttendance = classAttendance;
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
      }
    });
  }

  view(classAttendance: ClassAttendance) {
    this.router.navigate([this.baseURL + '/' + classAttendance.id ]);
  }

  onPageChange(pageEvent: PageEvent) {
      this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
      this.param.pageSize = pageEvent.pageSize;
  }

}
