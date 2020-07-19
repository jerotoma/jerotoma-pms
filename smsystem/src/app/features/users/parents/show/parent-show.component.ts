import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { Parent, ShowMessage  } from 'app/models';
import { USER_TYPE } from 'app/utils';


@Component({
  selector: 'app-parent-show',
  template: `<app-user-details
              *ngIf='parent'
              [userDatail]="parent"
              [userType]="userType"
              (onImageChangeSuccess)="reloadParentDetails($event)"
              ></app-user-details>`,
})
export class ParentShowComponent implements OnInit {

  parent: Parent = null;
  userType: USER_TYPE = USER_TYPE.PARENT;
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
        this.loadParentDetails(routeParam.id);
       // window.console.log(routeParam);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  reloadParentDetails(data: any) {
    this.loadParentDetails(data.id);
  }
  loadParentDetails(parentId: number) {
      this.userService.loadUser(parentId, this.userType).subscribe((parent: Parent) => {
        if (parent) {
          this.parent = parent;
        }
      });
  }

}
