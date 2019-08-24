import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ChangeDetectorRef, Inject  } from '@angular/core';
import { Router } from '@angular/router';


import { AuthService, TokenService, AUTH_CONSTANT } from './../../services/auth';
import { UserContext } from 'app/models/users/user-context';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  user: UserContext = {
    username: '',
    password: '',
    rememberMe: false,
  };
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  messages: string[] = [];
  errors: string[] = [];
  submitted: boolean = false;
  rememberMe: boolean = true;

  constructor(
        private router: Router,
        private authService: AuthService) {

   }

  ngOnInit() {

  }

  login(): void {
    this.messages = [];
    this.errors = [];
    this.showMessage.error = false;
    this.showMessage.success = false;
    this.authService.authenticate(this.user)
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.showMessage.success = true;
        this.processLoginResult(resp.body, status);
      } else {
        this.showMessage.error = true;
        this.errors.push(resp.error ? resp.error.message : resp.message);
      }
    }, error => {
      this.showMessage.error = true;
      this.errors.push(error ? error.error.message : '');
    });
  }

  getConfigValue(key: string) {

  }

  processLoginResult(data: any, status: number): void {
    if (status !== null && status === 200 && data && data.success) {
        if (this.authService.isAuthenticated()) {
          this.showMessage.error = false;
          this.showMessage.success = true;
          this.router.navigate(['/dashboard']);
        } else {
          this.errors.push('Invalid token');
          this.showMessage.error = true;
        }
    } else {
      this.errors.push(data.message);
      this.showMessage.error = true;
    }
    return;
  }
}
