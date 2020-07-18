import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { AuthService, SecurityClearanceService } from 'app/services';
import { ShowMessage, UserContext  } from 'app/models';
import { APP_CONSTANTS } from 'app/utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  appName: string = APP_CONSTANTS.appName;
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
  loginForm: FormGroup;

  constructor(
        private router: Router,
        private securityClearanceService: SecurityClearanceService,
        private formBuilder: FormBuilder,
        private authService: AuthService) {

   }

  ngOnInit() {
    this.loadRegisterForm();
  }

  login(): void {
    this.messages = [];
    this.errors = [];
    this.showMessage.error = false;
    this.showMessage.success = false;
    this.submitted = true;
    this.user = this.loginForm.value;
    this.authService.authenticate(this.user)
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      this.submitted = false;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.loginForm.reset();
        this.showMessage.success = true;
        this.processLoginResult(resp.body, status);
      } else {
        this.showMessage.error = true;
        this.errors.push(resp.error ? resp.error.message : resp.message);
      }
    });
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
  loadRegisterForm() {
    this.loginForm = this.formBuilder.group({
      username: [
        '',
        Validators.compose([
          Validators.required,
          Validators.email,
        ]),
      ],
      password: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(5),
        ]),
      ],
      rememberMe: [''],
    });

  }

  checkedChange(checked: boolean) {
    this.loginForm.patchValue({
      rememberMe: checked,
    });
  }
}
