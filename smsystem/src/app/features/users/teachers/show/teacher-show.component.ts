import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { USER_TYPE } from 'app/utils';
import { Teacher  } from 'app/models';


@Component({
  selector: 'app-teacher-show',
  templateUrl: 'teacher-show.component.html',
  styleUrls: ['teacher-show.component.scss'],
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

  reloadTeacherDetails(data: any) {
    this.loadTeacherDetails(data.id);
  }

  loadTeacherDetails(userId: number) {
      this.userService.loadUser(userId).subscribe((teacher: Teacher ) => {
        if (teacher) {
          this.teacher = teacher;
        }
      });
  }

}
