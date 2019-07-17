import { Component, OnInit, ChangeDetectorRef, Inject  } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbSpinnerService,
  } from '@nebular/theme';
import {
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
    private spinnerService: NbSpinnerService,
    service: NbAuthService,
    @Inject(NB_AUTH_OPTIONS) options: {},
    cd: ChangeDetectorRef,
    router: Router) {
    super(service, options, cd, router);
    console.log(this.service);
   }

  ngOnInit() {

  }

  login(): void {
    this.errors = [];
    this.messages = [];
    this.submitted = true;
    console.log(this.user);
    this.service.authenticate(this.strategy, this.user).subscribe((result: NbAuthResult) => {
      this.submitted = false;
      console.log(result);
      if (result.isSuccess()) {
        this.messages = result.getMessages();
      } else {
        this.errors = result.getErrors();
      }

      const redirect = result.getRedirect();
      if (redirect) {
        setTimeout(() => {
          return this.router.navigateByUrl(redirect);
        }, this.redirectDelay);
      }
      this.cd.detectChanges();
    });
  }
}
