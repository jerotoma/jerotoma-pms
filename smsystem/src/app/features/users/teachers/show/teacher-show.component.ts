import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { USER_TYPE } from 'app/utils';
import { Teacher  } from 'app/models';


@Component({
  selector: 'app-teacher-show',
  template: `<app-user-details
              *ngIf='teacher'
              [userDatail]="teacher"
              [userType]="userType"
              (onImageChangeSuccess)="reloadParentDetails($event)"
              ></app-user-details>`,
})
export class TeacherShowComponent implements OnInit {

  teacher: Teacher = null;
  userType: USER_TYPE = USER_TYPE.TEACHER;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) {

  }

  ngOnInit() {
    // For one time load
    let id = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParam => {
        this.loadTeacherDetails(routeParam.id);
       // window.console.log(routeParam);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  reloadParentDetails(data: any) {
    this.loadTeacherDetails(data.id);
  }

  loadTeacherDetails(teacherId: number) {
      this.userService.loadUser(teacherId, this.userType).subscribe((teacher: Teacher ) => {
        if (teacher) {
          this.teacher = teacher;
        }
      });
  }

}
