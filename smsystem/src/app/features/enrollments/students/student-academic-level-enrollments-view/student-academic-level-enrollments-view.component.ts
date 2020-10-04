import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { DeleteModalComponent } from 'app/shared';

import { StudentCourseEnrollmentCreateComponent } from '../student-academic-level-enrollment-create/student-academic-level-enrollment-create.component';
import { StudentAcademicLevelEnrollmentEditComponent } from '../student-academic-level-enrollment-edit/student-academic-level-enrollment-edit.component';

import { StudentAcademicLevel, ResponseWrapper } from 'app/models';
import { StudentAcademicLevelService } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-student-academic-level-enrollments-view',
  styleUrls: ['./student-academic-level-enrollments-view.component.scss'],
  templateUrl: './student-academic-level-enrollments-view.component.html',
})
export class StudentAcademicLevelEnrollmentsViewComponent implements OnInit {

  baseURL: string = '/dashboard/enrollments/students/';
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Scheduled Courses';
  studentClasses: StudentAcademicLevel[] = [];
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'studentNumber', 'classesCount',  'academicLevel', 'academicYear', 'action'];
  dataSource: MatTableDataSource<StudentAcademicLevel> = new MatTableDataSource<StudentAcademicLevel >();

  constructor(
    private studentClassService: StudentAcademicLevelService,
    private dialogService: NbDialogService,
    private router: Router,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadStudentClasses();
  }

  loadStudentClasses() {
    this.isLoading = true;
    this.studentClassService.getStudentAcademicLevels(this.param)
      .subscribe((result: ResponseWrapper) => {
        const resp = result;
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.studentClasses = data.studentClasses;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<StudentAcademicLevel>(data.studentClasses);
        }
      }, error => {

    });
  }

  open() {
    this.dialogService.open(StudentCourseEnrollmentCreateComponent, {
      context: {
        title: 'Enroll New Student',
        action: 'create',
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.loadStudentClasses();
      }
    });
  }

  edit(studentClass: StudentAcademicLevel) {
    this.dialogService.open(StudentAcademicLevelEnrollmentEditComponent, {
      context: {
        title: 'Edit Enrolled Student',
        studentAcademicLevel: studentClass,
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.loadStudentClasses();
      }
    });
  }

  delete(studentClass: StudentAcademicLevel) {
    this.dialogService.open(DeleteModalComponent, {
      context: {
        title: 'Delete Class',
        action: 'delete',
        id: studentClass.id.toString(),
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.deleteEnrolledStudent(result.id);
      }
    });
  }

  view(studentClass: StudentAcademicLevel) {
    this.router.navigate([this.baseURL + '/' + studentClass.id ]);
  }

  onPageChange(pageEvent: PageEvent) {
      this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
      this.param.pageSize = pageEvent.pageSize;
      this.loadStudentClasses();
  }
  deleteEnrolledStudent(studentClassId: number) {
    this.studentClassService.deleteStudentAcademicLevel(studentClassId).subscribe((result: boolean ) => {
      if (result) {
        this.loadStudentClasses();
      }
    });
  }
}
