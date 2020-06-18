import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';
import { PageEvent, MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { NbDialogService } from '@nebular/theme';
import { MatTableDataSource} from '@angular/material/table';
import { DeleteModalComponent } from 'app/shared';

import { ClassAttendenceCreateComponent } from '../classes/class-attendance-create/class-attendance-create.component';

import {StudentAttendance, ClassAttendance, ResponseWrapper } from 'app/models';
import {StudentAttendanceService, ClassAttendanceService } from 'app/services';
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
  currentClassAttendanceId: number;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  classAttendanceId: number;
  displayedColumns: string[] = ['id', 'fullName', 'courseName',  'academicYearName', 'yearOfStudy', 'attendanceDate', 'statusName', 'action'];
  dataSource: MatTableDataSource<StudentAttendance> = new MatTableDataSource<StudentAttendance>();
  studentAttendances: StudentAttendance[];
  classAttendance: ClassAttendance;
  classAttendances: ClassAttendance[];
  isRecordTabActive: boolean = false;

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
    private studentAttendanceService: StudentAttendanceService,
    private route: ActivatedRoute,
    private router: Router,
    ) {
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.classAttendanceId = params['classAttendanceId'];
      if (this.classAttendanceId) {
        this.isRecordTabActive = true;
        this.loadClassAttendancesByClassAttendanceId(this.classAttendanceId);
      }
    });
    this.loadClassAttendances();
    this.loadStudentAttendances();
  }

  loadStudentAttendances() {
    this.studentAttendanceService.loadStudentAttendancesPaginated(this.param).subscribe((result: ResponseWrapper) => {
      const resp = result;
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.studentAttendances = data.studentAttendances;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<StudentAttendance>(data.studentAttendances);
        }
    }, error => {
      this.isLoading = false;
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
  onRecordSubmitted(success: boolean) {
    if (success) {
      this.loadStudentAttendances();
    }
  }

  onSelectedChange(classAttendanceId: number) {
     const URL: string = '/dashboard/attendances/students';
     const navigationExtras: NavigationExtras = {
      queryParams: {classAttendanceId: classAttendanceId },
    };
    this.router.navigate([URL], navigationExtras);
  }

  loadClassAttendancesByClassAttendanceId(classAttendanceId: number) {
    this.isLoading = true;
    this.classAttendance = null;
    this.classAttendanceService.getClassAttendance(classAttendanceId)
    .subscribe((classAttendance: ClassAttendance) => {
        this.isLoading = false;
        if (classAttendance) {
          this.classAttendance = classAttendance;
          this.currentClassAttendanceId = classAttendance.id;
        }
    }, error => {
      this.isLoading = false;
    });
  }

  loadClassAttendances() {
    this.isLoading = true;
    this.classAttendanceService.loadClassAttendances()
    .subscribe((classAttendances: ClassAttendance[]) => {
        this.isLoading = false;
        if (classAttendances) {
          this.classAttendances = classAttendances;
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
