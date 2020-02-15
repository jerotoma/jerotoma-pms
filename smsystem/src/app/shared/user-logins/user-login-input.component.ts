import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { UserLoginInput, UserLoginInputWrapper } from 'app/models';
import { CheckBoxValidator, MustMatch } from 'app/utils';

@Component({
  selector: 'app-user-login-input',
  templateUrl: './user-login-input.component.html',
  styleUrls: ['./user-login-input.component.scss']
})
export class UserLoginInputComponent implements OnInit {

  @Input() isResetForm: boolean = false;
  @Input('userLoginInput') userLoginInput: UserLoginInput;
  @Output() onChanges: EventEmitter<UserLoginInputWrapper> = new EventEmitter();

  canUserLogin: boolean = false;
  userLoginInputForm: FormGroup;
  userLoginInputWrapper: UserLoginInputWrapper;

  constructor( private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.loadPasswordForm();
    if (this.userLoginInput) {
      this.patchPasswordValue(this.userLoginInput);
    }
}

loadPasswordForm() {
  this.userLoginInputForm = this.formBuilder.group({
    canUserLogin: [false, Validators.required],
    email: [
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
  },
  {
    validator: [
      MustMatch('password', 'confirmPassword'),
      CheckBoxValidator('canUserLogin'),
    ],
  });
  this.processFormChange();
}

processFormChange() {
  this.userLoginInputForm.valueChanges.subscribe(value => {
    this.userLoginInputWrapper = {
      userLoginInput: value,
      isValid: this.userLoginInputForm.valid,
      canUserLogin: this.canUserLogin,
    };
    this.onChanges.emit(this.userLoginInputWrapper);
  });
}

resetForm() {
  this.userLoginInputForm.reset();
}
patchPasswordValue(userLoginInput: UserLoginInput) {
  this.userLoginInputForm.patchValue({
    email: userLoginInput.email,
    password: userLoginInput.password,
    confirmPassword: userLoginInput.confirmPassword,
    canUserLogin: this.canUserLogin,
  });
}

checkedChange(checked: boolean) {
  this.canUserLogin = checked;
  this.userLoginInputForm.patchValue({
    canUserLogin: checked,
  });

  if (!checked) {
    this.userLoginInputForm.patchValue({
      password: '',
      confirmPassword: '',
    });
  }
}

}
