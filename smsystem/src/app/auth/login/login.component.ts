import { Component, OnInit, ChangeDetectorRef, Inject  } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbSpinnerService,
  } from '@nebular/theme';
import {
    NbLoginComponent,
    NbAuthService,
    NB_AUTH_OPTIONS,
    NbAuthSocialLink } from '@nebular/auth';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent extends NbLoginComponent implements OnInit {

  socialLinks: NbAuthSocialLink[];

  constructor(
    private spinnerService: NbSpinnerService,
    service: NbAuthService,
    @Inject(NB_AUTH_OPTIONS) options: {},
    cd: ChangeDetectorRef,
    router: Router) {
    super(service, options, cd, router);
   }

  ngOnInit() {
    this.spinnerService.load();
  }

}
