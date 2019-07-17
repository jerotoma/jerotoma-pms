import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbRegisterComponent,
  NbAuthService,
  NB_AUTH_OPTIONS,
  NbAuthSocialLink } from '@nebular/auth';

@Component({
  selector: 'ngx-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent extends NbRegisterComponent implements OnInit {

  submitted = false;
  errors: string[] = [];
  messages: string[] = [];
  user: any = {};
  socialLinks: NbAuthSocialLink[] = [];

  constructor(protected service: NbAuthService,
    @Inject(NB_AUTH_OPTIONS)
    protected options = {},
    protected cd: ChangeDetectorRef,
    protected router: Router) {
      super(service, options, cd, router );
    }

  ngOnInit() {
  }

}
