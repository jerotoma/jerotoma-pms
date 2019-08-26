import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { Teacher, ShowMessage  } from 'app/models';


@Component({
  selector: 'app-teacher-show',
  template: `<app-user-details
              *ngIf='teacher'
              [userDatail]="teacher"
              [userType]="'teacher'"
              ></app-user-details>`,
})
export class TeacherShowComponent implements OnInit {

  teacher: Teacher = null;
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
        this.loadTeacherDetails(routeParam.id);
       window.console.log(routeParam);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  loadTeacherDetails(teacherId: number) {
      this.userService.loadUser(teacherId, 'teacher').subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200) {
          this.teacher = resp.body.data;
        }
      }, error => {
        this.showMessage.error = true;
        this.showMessage.success = false;
        this.showMessage.message = error ? error.error.message : '';
      });
  }

}
