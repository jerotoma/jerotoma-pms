import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbResetPasswordComponent,
  NbAuthService,
  NB_AUTH_OPTIONS,
  NbAuthSocialLink } from '@nebular/auth';


@Component({
  selector: 'ngx-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent extends NbResetPasswordComponent implements OnInit {

  constructor(protected service: NbAuthService,
    @Inject(NB_AUTH_OPTIONS) protected options = {},
    protected cd: ChangeDetectorRef,
    protected router: Router) {
      super(service, options, cd, router );
    }

  ngOnInit() {
  }

}
