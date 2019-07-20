import { messages } from './../../features/extra-components/chat/messages';
import { Component, OnInit, ChangeDetectorRef, Inject  } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbSpinnerService,
  } from '@nebular/theme';
import {
  NbTokenService,
  NbAuthToken,
  NbAuthResult,
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
    service: NbAuthService,
    @Inject(NB_AUTH_OPTIONS) options: {},
    cd: ChangeDetectorRef,
    router: Router,
    tokenService: NbTokenService ) {
    super(service, options, cd, router);
   }

  ngOnInit() {

  }

  login(): void {
    this.errors = [];
    this.messages = [];
    this.submitted = true;
    this.service.authenticate(this.strategy, this.user).subscribe((result: NbAuthResult) => {
      this.submitted = false;
      if (result.isSuccess()) {
        const response = result.getResponse().body;
        this.messages = result.getMessages();
        if (response.success) {
          this.messages.push(response.message);
          setTimeout(() => {
            return this.router.navigateByUrl('/dashboard');
          }, this.redirectDelay);
        } else {
          this.errors.push(response.message);
        }
      } else {
        const response = result.getResponse();
        this.errors = result.getErrors();
        this.errors.push(response.message);
        this.errors.push(response.error.message);
      }
      this.cd.detectChanges();
    });
  }
}
