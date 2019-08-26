import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { Staff, ShowMessage  } from 'app/models';


@Component({
  selector: 'app-staff-show',
  template: `<app-user-details
              *ngIf='staff'
              [userDatail]="staff"
              [userType]="'staff'"
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
    let id = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParam => {
        this.loadStaffDetails(routeParam.id);
       window.console.log(routeParam);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  loadStaffDetails(staffId: number) {
      this.userService.loadUser(staffId, 'staff').subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200) {
          this.staff = resp.body.data;
        }
      }, error => {
        this.showMessage.error = true;
        this.showMessage.success = false;
        this.showMessage.message = error ? error.error.message : '';
      });
  }

}
