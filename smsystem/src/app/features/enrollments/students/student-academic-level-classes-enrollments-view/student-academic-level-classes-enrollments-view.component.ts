import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { NbDialogService } from '@nebular/theme';
import { MatPaginator} from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeleteModalComponent } from 'app/shared';
import { AcademicLevelStudentEnrollmentCreateComponent, AcademicLevelClassesEnrollmentCreateComponent } from 'app/shared';
import { StudentAcademicLevelClassesEnrollmentEditComponent } from '../student-academic-level-classes-enrollment-edit/student-academic-level-classes-enrollment-edit.component';

import { StudentAcademicLevel, ResponseWrapper, CompletionStatus } from 'app/models';
import { StudentAcademicLevelService } from 'app/services';
import { QueryParam, USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-student-academic-level-classes-enrollments-view',
  styleUrls: ['./student-academic-level-classes-enrollments-view.component.scss'],
  templateUrl: './student-academic-level-classes-enrollments-view.component.html',
})
export class StudentAcademicLevelClassesEnrollmentsViewComponent implements OnInit {

  baseURL: string = '/dashboard/enrollments/student-academic-levels/';
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

  title: string = 'List of Student Classes';
  studentClasses: StudentAcademicLevel[] = [];
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'classesCount', 'program', 'academicLevel', 'academicYear', 'completionStatusName', 'action'];
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
    this.dialogService.open(AcademicLevelClassesEnrollmentCreateComponent, {
      context: {
        title: 'Enroll New Student',
        action: 'create',
        student: null,
        useSearch: true,
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.loadStudentClasses();
      }
    });
  }

  enrollStudentAcademicLevel() {
    this.dialogService.open(AcademicLevelStudentEnrollmentCreateComponent , {
      context: {
        title: 'Enroll Student Academic Level',
        action: 'create',
        student: null,
        useSearch: true,
        userType: USER_TYPE.STUDENT
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.loadStudentClasses();
      }
    });
  }

  edit(studentAcademicLevel: StudentAcademicLevel) {
    this.dialogService.open(StudentAcademicLevelClassesEnrollmentEditComponent , {
      context: {
        title: 'Edit Enrolled Student',
        studentAcademicLevel: studentAcademicLevel,
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.loadStudentClasses();
      }
    });
  }

  delete(studentAcademicLevel: StudentAcademicLevel) {
    this.dialogService.open(DeleteModalComponent, {
      context: {
        title: 'Delete Class',
        action: 'delete',
        id: studentAcademicLevel.id.toString(),
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        this.deleteEnrolledStudent(result.id);
      }
    });
  }

  view(studentAcademicLevel: StudentAcademicLevel) {
    this.router.navigate([this.baseURL + '/' + studentAcademicLevel.id ]);
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

  getCompletionStatus(completionStatus: string): string {
    if (CompletionStatus.IN_PROGRESS === completionStatus) {
      return 'info';
    } else if (CompletionStatus.COMPLETED === completionStatus) {
      return 'success';
    } else if (CompletionStatus.NOT_STARTED === completionStatus) {
      return 'warning';
    }
    return 'danger';
  }
}
