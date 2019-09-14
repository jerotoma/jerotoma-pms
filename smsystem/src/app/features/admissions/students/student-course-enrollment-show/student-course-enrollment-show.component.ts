import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { StudentClass, Student } from 'app/models';
import { StudentClassService } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-student-course-enrollment-show',
  styleUrls: ['./student-course-enrollment-show.component.scss'],
  templateUrl: './student-course-enrollment-show.component.html',
})
export class StudentCourseEnrollmentShowComponent implements OnInit {
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
  studentClass: StudentClass;
  student: Student;

  constructor(
    private studentClassService: StudentClassService,
    private route: ActivatedRoute,
    private router: Router,
    ) {
  }
  ngOnInit() {
    // For one time load
    // let id = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParam => {
        this.loadStudentClass(routeParam.id);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  loadStudentClass(studentId: number) {
    this.studentClassService.getStudentClass(studentId)
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        window.console.log(result);
        const status = resp.status;
        if (status !== null && status === 200 && resp.body) {
          const data = resp.body.data;
          this.studentClass = data;
          this.student = this.studentClass.student;


        }
      }, error => {

    });
  }


}
