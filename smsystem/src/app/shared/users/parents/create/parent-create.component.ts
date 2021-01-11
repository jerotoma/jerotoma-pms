import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { AddressComponent } from 'app/shared/addresses';
import { UserLoginInputComponent } from 'app/shared/user-logins';

import {
  Address,
  AddressWrapper,
  Parent,
  ParentWrapper,
  UserLoginInput,
  UserLoginInputWrapper,
} from 'app/models';
import { AcademicDisciplineService, PositionService } from 'app/services';
import { DateFormatter, USER_TYPE, APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-parent-create',
  templateUrl: 'parent-create.component.html',
  styleUrls: ['parent-create.component.scss'],
})
export class ParentCreateComponent implements OnInit {
  title: string = 'Create New Parent';
  @Output() onChanges: EventEmitter<ParentWrapper> = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  @ViewChild(UserLoginInputComponent, {static: false}) appPassword: UserLoginInputComponent;
  action: string = APP_ACTION_TYPE.create;
  userType: string = USER_TYPE.PARENT;
  parentForm: FormGroup;
  addressForm: FormGroup;
  address: Address;
  userLoginInput: UserLoginInput;
  studentIds: number[]  = [];
  parent: Parent;
  parentWrapper: ParentWrapper;

  constructor(
    protected positionService: PositionService,
    protected academicDisciplineService: AcademicDisciplineService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadParentForm();
  }

  resetForms() {
    this.parentForm.reset();
    this.appAddress.resetForm();
    if (this.action === APP_ACTION_TYPE.create) {
      this.appPassword.resetForm();
    }
  }

  loadParentForm() {
    this.parentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      middleNames: [null],
      occupation: [null],
      phoneNumber: [null, Validators.required],
      emailAddress: [null, Validators.compose([
        Validators.email,
      ])],
      relationshipType: [null, Validators.required],
      userLoginInput: [null],
      gender: ['', Validators.required],
      userId: [null],
      picture: [''],
      userType: [USER_TYPE.PARENT],
      address: [null, Validators.required],
      studentIDs: [''],
      studentFullName: [''],
    });

    this.parentForm.valueChanges.subscribe((parent: Parent) => {
      this.parentWrapper = {parent: parent, valid: this.parentForm.valid};
      this.onChanges.emit(this.parentWrapper);
    });
  }

  onParentAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.parentForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.parentForm.controls['address'].setErrors(null);
      this.parentForm.patchValue({address: addressWrapper.address});
    }
  }

  onUserLoginInputChange(userLoginInputWrapper: UserLoginInputWrapper) {
    if (userLoginInputWrapper.isValid) {
        this.parentForm.patchValue({
          userLoginInput: userLoginInputWrapper.userLoginInput,
        });
        this.parentForm.controls['userLoginInput'].setErrors(null);
        // window.console.log(userLoginInputWrapper);
    } else {
      this.parentForm.controls['userLoginInput'].setErrors({ invalidUsername: true });
    }
  }

  updateUseInput(parent: Parent) {
    this.parentForm.patchValue({
      id: parent.id,
      firstName: parent.firstName,
      lastName: parent.lastName,
      position: parent.position ? this.parent.position.id : null,
      occupation: parent.occupation ? this.parent.occupation : 'Parent',
      employmentCode: parent.userCode,
      gender: parent.gender,
      picture: parent.picture,
      userId: parent.userId,
      middleNames: parent.middleNames,
      phoneNumber: parent.phoneNumber,
      emailAddress: parent.emailAddress,
      relationshipType: parent.relationshipType,
      birthDate: DateFormatter(parent.birthDate, 'YYYY/MM/DD', false),
      userType: USER_TYPE.PARENT,
      academicDiscipline: parent.academicDiscipline ? parent.academicDiscipline.id : null,
      fullName: parent.fullName,
      address: parent.address ? parent.address : null,
    });

    if (parent.address) {
      this.appAddress.patchAddressValue(parent.address);
    }
  }
}
