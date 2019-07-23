import { merge } from 'rxjs';
import { messages } from './../../features/extra-components/chat/messages';
import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';

import {
  NbAuthResult,
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

  register(): void {
    this.errors = [];
    this.messages = [];
    this.submitted = true;
    this.service.register(this.strategy, this.user).subscribe((result: NbAuthResult) => {
      this.submitted = false;
      if (result.isSuccess()) {
        const response = result.getResponse().body;
        if (response.success) {
          this.messages.push(response.message);
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
