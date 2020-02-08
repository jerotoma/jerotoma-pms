import { User } from './../../../../models/users/user.model';
import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { Student, ShowMessage  } from 'app/models';


@Component({
  selector: 'app-student-show',
  template: `<app-user-details
              *ngIf='student'
              [userDatail]="student"
              [userType]="'student'"
              ></app-user-details>`,
})
export class StudentShowComponent implements OnInit {

  student: Student = null;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) {

  }

  ngOnInit() {
    // For one time load
    let id = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParam => {
        this.loadStudentDetails(routeParam.id);
       window.console.log(routeParam);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  loadStudentDetails(studentId: number) {
      this.userService.loadUser(studentId, 'student').subscribe((user: Student) => {
       if (user) {
          this.student = user;
        }
      });
  }

}
