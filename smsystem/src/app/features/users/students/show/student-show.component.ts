import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { Student, ShowMessage  } from 'app/models';
import { USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-student-show',
  templateUrl: 'student-show.component.html',
  styleUrls: ['student-show.component.scss'],
})
export class StudentShowComponent implements OnInit {

  student: Student = null;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  userType: string = USER_TYPE.STUDENT;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) {

  }

  ngOnInit() {
    // For one time load
    // let id = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParam => {
        this.loadStudentDetails(routeParam.id);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  reloadParentDetails(data: any) {
    this.loadStudentDetails(data.id);
  }

  loadStudentDetails(userId: number) {
      this.userService.loadUser(userId).subscribe((user: Student) => {
       if (user) {
          this.student = user;
        }
      });
  }

}
