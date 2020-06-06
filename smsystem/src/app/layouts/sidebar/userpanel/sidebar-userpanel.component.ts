import { Component, OnDestroy, OnInit } from '@angular/core';
import { NbAuthService } from '@nebular/auth';

import { User } from 'app/models';
import {UserService } from 'app/services';


@Component({
  selector: 'app-sidebar-userpanel',
  styleUrls: ['./sidebar-userpanel.component.scss'],
  templateUrl: './sidebar-userpanel.component.html',
})
export class SidebarUserPanelComponent implements OnInit, OnDestroy {

  user: User;

  constructor(
    private userService: UserService,
    private authService: NbAuthService,
    ) {
  }

  ngOnInit(): void {
    this.loadCurrentUser();
  }
  ngOnDestroy(): void {

  }

  loadCurrentUser() {
    if (this.authService.isAuthenticatedOrRefresh()) {
      this.userService.getCurrentUser().subscribe((user: User) => {
        this.user = user;
      });
    }
  }

  onlineClicked(e: Event) {
    e.preventDefault();
  }
}
