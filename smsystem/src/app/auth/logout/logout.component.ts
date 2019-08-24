import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {AuthService } from 'app/services/auth';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {
  redirectUrl: string = '/account/login';
  strategyName: string = 'email';
  errors: string[];
  constructor(
    protected service: AuthService,
    protected router: Router) {

    }

  ngOnInit() {
    this.logout();
  }

  logout() {
    if (!this.service.isAuthenticated()) {
      this.router.navigate([this.redirectUrl]);
      return;
    }
    this.service.logout().subscribe((res: any) => {

    });
  }
}
