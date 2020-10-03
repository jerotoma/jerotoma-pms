import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {AuthService, SecurityClearanceService } from 'app/services';

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
    private securityClearanceService: SecurityClearanceService,
    protected router: Router) {

    }

  ngOnInit() {
    this.checkAuthenticationStatusAndLogout();
  }

  logout() {
    this.service.logout().subscribe((res: any) => {
      this.securityClearanceService.distroyCurrentUser();
    });
  }
  checkAuthenticationStatusAndLogout() {
      if (!this.service.isAuthenticated()) {
        this.router.navigate([this.redirectUrl]);
        return;
      }
      this.logout();
  }
}
