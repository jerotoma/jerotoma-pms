import { merge } from 'rxjs';
import { messages } from './../../features/extra-components/chat/messages';
import { Component, OnInit, Inject, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from './../../services/auth/auth.service';
import { UserContext } from './../../models/users/user-context';
import { ShowMessage } from './../../models/messages/show-message';


@Component({
  selector: 'ngx-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  submitted = true;
  errors: string[] = [];
  messages: string[] = [];
  user: any = {};
  constructor(
    protected service: AuthService,
    protected router: Router) {}

  ngOnInit() {
  }

  register(): void {
    this.errors = [];
    this.messages = [];
    this.submitted = true;
    this.service.register(this.user).subscribe((result: any) => {
      this.submitted = false;
    });
  }

  getConfigValue(key: string) {

  }

}
