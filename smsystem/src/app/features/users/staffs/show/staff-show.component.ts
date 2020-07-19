import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { Staff, ShowMessage  } from 'app/models';

import { USER_TYPE } from 'app/utils';


@Component({
  selector: 'app-staff-show',
  template: `<app-user-details
              *ngIf='staff'
              [userDatail]="staff"
              [userType]="'staff'"
              (onImageChangeSuccess)="reloadParentDetails($event)"
              ></app-user-details>`,
})
export class StaffShowComponent implements OnInit {

  staff: Staff = null;
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
    // let id = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParam => {
      this.loadStaffDetails(routeParam.id);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  reloadParentDetails(data: any) {
    this.loadStaffDetails(data.id);
  }

  loadStaffDetails(staffId: number) {
      this.userService.loadUser(staffId, USER_TYPE.STAFF).subscribe((staff: Staff) => {
          this.staff = staff;
        });
  }

}
