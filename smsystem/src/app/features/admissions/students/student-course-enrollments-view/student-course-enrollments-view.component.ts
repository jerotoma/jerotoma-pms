import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { DeleteModalComponent } from 'app/shared';

import { StudentCourseEnrollmentCreateComponent } from '../student-course-enrollment-create/student-course-enrollment-create.component';
import { StudentCourseEnrollmentEditComponent } from '../student-course-enrollment-edit/student-course-enrollment-edit.component';

import { StudentClass, ResponseWrapper } from 'app/models';
import { StudentClassService } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-student-course-enrollments-view',
  styleUrls: ['./student-course-enrollments-view.component.scss'],
  templateUrl: './student-course-enrollments-view.component.html',
})
export class StudentCourseEnrollmentsViewComponent implements OnInit {

  baseURL: string = '/dashboard/admissions/students/';
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
  studentClasses: StudentClass[] = [];
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'studentNumber', 'numberOfClasses',  'academicYearTerm', 'academicYear', 'action'];
  dataSource: MatTableDataSource<StudentClass> = new MatTableDataSource<StudentClass >();

  constructor(
    private studentClassService: StudentClassService,
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
    this.studentClassService.getStudentClasses(this.param)
      .subscribe((result: ResponseWrapper) => {
        const resp = result;
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.studentClasses = data.studentClasses;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<StudentClass>(data.studentClasses);
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
    }).onClose.subscribe(_data => {
      this.loadStudentClasses();
    });
  }

  edit(studentClass: StudentClass) {
    this.dialogService.open(StudentCourseEnrollmentEditComponent, {
      context: {
        title: 'Edit Enrolled Student',
        studentId: studentClass.student.id.toString(),
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {

      }
    });
  }

  delete(studentClass: StudentClass) {
    this.dialogService.open(DeleteModalComponent, {
      context: {
        title: 'Delete Class',
        action: 'delete',
        id: studentClass.id.toString(),
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.loadStudentClasses();
      }
    });
  }

  view(studentClass: StudentClass) {
    this.router.navigate([this.baseURL + '/' + studentClass.student.id ]);
  }

  onPageChange(pageEvent: PageEvent) {
      this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
      this.param.pageSize = pageEvent.pageSize;
      this.loadStudentClasses();
  }
  deleteEnrolledStudent(studentClassId: number) {
    this.studentClassService.deleteStudentClass(studentClassId).subscribe((result: boolean ) => {
      if (result) {
        this.loadStudentClasses();
      }
    });
  }
}
