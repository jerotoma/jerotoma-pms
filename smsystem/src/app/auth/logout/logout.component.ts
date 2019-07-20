import { Component, OnInit, ChangeDetectorRef, Inject  } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbSpinnerService,
  } from '@nebular/theme';
import {
  NbLogoutComponent,
  NbTokenService,
  NbAuthService,
  NB_AUTH_OPTIONS,
  NbAuthResult,
  NbAuthSocialLink } from '@nebular/auth';
@Component({
  selector: 'ngx-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent extends NbLogoutComponent implements OnInit {
  errors: string[];
  constructor(
    private tokenService: NbTokenService,
    protected service: NbAuthService,
    @Inject(NB_AUTH_OPTIONS)
    protected options = {},
    protected router: Router) {
      super(service, options, router);
    }

  ngOnInit() {
    this.logout();
  }

  logout(){
    this.service.logout('email').subscribe((result: NbAuthResult) => {
      window.console.log(result);
      if (result.isSuccess()) {
        const response = result.getResponse().body;
        if (response.success) {
          this.router.navigate(['/account/login']);        }
      } else {
        const response = result.getResponse();
        this.errors = result.getErrors();
        this.errors.push(response.message);
        this.errors.push(response.error.message);
      }
    });
  }

}
