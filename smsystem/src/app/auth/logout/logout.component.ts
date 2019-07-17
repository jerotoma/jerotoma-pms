import { Component, OnInit, ChangeDetectorRef, Inject  } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbSpinnerService,
  } from '@nebular/theme';
import {
  NbLogoutComponent,
  NbAuthService,
  NB_AUTH_OPTIONS,
  NbAuthSocialLink } from '@nebular/auth';
@Component({
  selector: 'ngx-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent extends NbLogoutComponent implements OnInit {

  constructor(
    protected service: NbAuthService,
    @Inject(NB_AUTH_OPTIONS)
    protected options = {},
    protected router: Router) {
      super(service, options, router);
    }

  ngOnInit() {
  }

}
