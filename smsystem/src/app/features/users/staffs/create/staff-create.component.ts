import { Component, OnInit, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { AddressComponent, UserLoginInputComponent } from 'app/shared';
import { NbDialogRef } from '@nebular/theme';
import { Staff, Parent, AddressWrapper, Address, UserLoginInput, UserLoginInputWrapper, ShowMessage, Position, ResponseWrapper } from 'app/models';
import { UserService } from 'app/services/users';
import { PositionService, ModalService  } from 'app/services';
import { QueryParam , DateValidator, DateFormatter, USER_TYPE, APP_ACTION_TYPE} from 'app/utils';

@Component({
  selector: 'app-staff-create',
  templateUrl: 'staff-create.component.html',
  styleUrls: ['staff-create.component.scss'],
})
export class StaffCreateComponent implements OnInit, AfterViewInit {
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  @ViewChild(UserLoginInputComponent, {static: false}) appPassword: UserLoginInputComponent;
  @Output() onUserCreationSuccess = new EventEmitter();

  title: string = 'Create New Staff';

  action: string = 'create';
  position: number;
  positions: Position[] = [];

  staffForm: FormGroup;
  parentForm: FormGroup;
  addressForm: FormGroup;
  staff: Staff;
  address: Address;
  userLoginInput: UserLoginInput;
  parent: Parent;
  userId: number;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  linearMode = true;

  toggleLinearMode() {
    this.linearMode = !this.linearMode;
  }

  constructor(
    protected positionService: PositionService,
    private userService:  UserService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<StaffCreateComponent>) {}

  ngOnInit() {
    this.loadPositionList();
    this.loadStaffForm();
  }

  ngAfterViewInit() {
    if (this.action === APP_ACTION_TYPE.edit) {
      this.loadStaff(this.userId);
    }
  }

  dismiss() {
    this.ref.close();
  }

  onSubmit() {
   if (this.staffForm.valid ) {
      this.postData(this.staffForm.value);
    }
  }

  postData(data: Staff) {
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === APP_ACTION_TYPE.edit) {
      this.updateData(data);
    } else {
      this.userService.addUser(data).subscribe((resp: ResponseWrapper ) => {
        if (resp && resp.success) {
          this.modalService.openSnackBar('New Staff has been created', 'success');
          this.resetForms();
        }
      });
    }
  }

  updateData(data: Staff) {
    this.userService.updateUser(data).subscribe((resp: ResponseWrapper) => {
      if (resp && resp.success) {
        this.resetForms();
        this.modalService.openSnackBar('Staff has been updated', 'success');
      }
    });
  }

  resetForms() {
    this.staffForm.reset();
    this.appAddress.resetForm();
    if (this.action === APP_ACTION_TYPE.create) {
      this.appPassword.resetForm();
    }
    this.ref.close();
  }

  loadStaffForm() {
    this.staffForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      middleNames: [null],
      phoneNumber: ['', Validators.required],
      emailAddress: [null],
      username: [null],
      password: [null],
      userId: [null],
      confirmPassword: [null],
      position: ['', Validators.required],
      occupation: ['staff'],
      gender: ['', Validators.required],
      picture: [''],
      userType: [USER_TYPE.STAFF],
      birthDate: ['', DateValidator()],
      address: [null, Validators.required],
    });
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: USER_TYPE.STAFF,
    };
  }

  loadStaff(userId: number) {
    this.userService.loadUser(userId).subscribe((staff: Staff ) => {
      if (staff) {
        this.staff = staff;
        this.staffForm.patchValue({
          id: this.staff.id,
          firstName: this.staff.firstName,
          userId: this.staff.userId,
          lastName: this.staff.lastName,
          middleNames: this.staff.middleNames,
          occupation: this.staff.occupation,
          position: this.staff.position.id ,
          gender: this.staff.gender,
          phoneNumber: this.staff.phoneNumber,
          picture: this.staff.picture,
          birthDate: DateFormatter(this.staff.birthDate, 'YYYY/MM/DD', false),
          userType: USER_TYPE.STAFF,
          fullName: this.staff.fullName,
          address: this.staff.address,
        });
        this.appAddress.patchAddressValue(this.staff.address);
      }
    }, error => {

    });
  }

  onAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.staffForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.staffForm.controls['address'].setErrors(null);
      this.staffForm.patchValue({address: addressWrapper.address});

    }
  }

  onUserLoginInputChange(userLoginInputWrapper: UserLoginInputWrapper) {
    if (userLoginInputWrapper.isValid) {
        this.userLoginInput = userLoginInputWrapper.userLoginInput;
        this.staffForm.patchValue({
          username: this.userLoginInput.email,
          password: this.userLoginInput.password,
          confirmPassword: this.userLoginInput.confirmPassword,
        });
       // window.console.log(userLoginInputWrapper);
    }
  }

  loadPositionList() {
    this.positionService.loadPositionList().subscribe((positions: Position[]) => {
      if (positions) {
        this.positions = positions;
      }
    });
  }
}
