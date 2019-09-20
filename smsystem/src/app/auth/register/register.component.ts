import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CheckBoxValidator, MustMatch } from 'app/utils';
import { AuthService } from 'app/services/auth/auth.service';
import { ShowMessage } from 'app/models/messages/show-message.model';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  submitted = false;
  errors: string[] = [];
  messages: string[] = [];
  user: any = {};
  registerForm: FormGroup;
  constructor(
    protected authService: AuthService,
    private formBuilder: FormBuilder,
    protected router: Router) {}

  ngOnInit() {
    this.loadRegisterForm();
  }

  register(): void {
    this.errors = [];
    this.messages = [];
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    this.user = this.registerForm.value;
    this.authService.register(this.user).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      this.submitted = false;
      const resp = result;
      const data = result.body;
      if (data.success) {
        this.showMessage.success = true;
        this.messages.push('User has been created');
      } else {
        this.showMessage.error = true;
        this.errors.push(data.message);
      }
    }, error => {
      this.showMessage.error = true;
      this.errors.push(error ? error.error.message : '');
    });
  }

  loadRegisterForm() {
    this.registerForm = this.formBuilder.group({
      id: [null],
      firstName: [
        '', Validators.required,
      ],
      lastName: [
        '',
        Validators.required,
      ],
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
      confirmPassword: [
        '',
        Validators.required,
      ],
      terms: [
        '',
        Validators.required,
      ],
    },
    {
      validator: [
        MustMatch('password', 'confirmPassword'),
        CheckBoxValidator('terms'),
      ],
    });

  }

  checkedChange(checked: boolean) {
    this.registerForm.patchValue({
      terms: checked,
    });
  }
}
