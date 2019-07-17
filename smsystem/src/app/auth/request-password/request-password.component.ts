import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbRequestPasswordComponent,
  NbAuthService,
  NB_AUTH_OPTIONS,
  NbAuthSocialLink } from '@nebular/auth';

@Component({
  selector: 'ngx-request-password',
  templateUrl: './request-password.component.html',
  styleUrls: ['./request-password.component.scss'],
})
export class RequestPasswordComponent extends NbRequestPasswordComponent implements OnInit {

  constructor(protected service: NbAuthService,
    @Inject(NB_AUTH_OPTIONS) protected options = {},
    protected cd: ChangeDetectorRef,
    protected router: Router) {
      super(service, options, cd, router );
     }

  ngOnInit() {
  }

}
