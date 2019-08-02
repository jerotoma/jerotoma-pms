import { Component, OnInit, ChangeDetectorRef, Inject  } from '@angular/core';
import { Router } from '@angular/router';

import {TokenService, AuthService } from '../..//services/auth';

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
    private tokenService: TokenService,
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
