import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from 'app/services/users/user.service';
import { Parent, ShowMessage  } from 'app/models';


@Component({
  selector: 'app-parent-show',
  template: `<app-user-details
              *ngIf='parent'
              [userDatail]="parent"
              [userType]="'parent'"
              ></app-user-details>`,
})
export class ParentShowComponent implements OnInit {

  parent: Parent = null;
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

  loadParentDetails(parentId: number) {
      this.userService.loadUser(parentId, 'parent').subscribe((parent: Parent) => {
        if (parent) {
          this.parent = parent;
        }
      });
  }

}
