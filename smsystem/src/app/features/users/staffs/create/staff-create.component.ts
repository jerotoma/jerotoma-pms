import { Component, OnInit, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { AddressComponent } from 'app/shared';
import { NbDialogRef } from '@nebular/theme';
import { Staff, Parent, AddressWrapper, Address, ShowMessage, Position } from 'app/models';
import { UserService } from 'app/services/users';
import { PositionService } from 'app/services/positions';
import { QueryParam , DateValidator, DateFormatter } from 'app/utils';

@Component({
  selector: 'app-staff-create',
  templateUrl: 'staff-create.component.html',
  styleUrls: ['staff-create.component.scss'],
})
export class StaffCreateComponent implements OnInit, AfterViewInit {
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
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
  parent: Parent;
  staffId: string;
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
    protected ref: NbDialogRef<StaffCreateComponent>) {}

  ngOnInit() {
    this.loadPositionList();
    this.loadStaffForm();
  }

  ngAfterViewInit() {
    if (this.action === 'edit') {
      this.loadStaff(parseInt(this.staffId, 10));
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
    if (this.action === 'edit') {
      this.updateData(data);
    } else {
      this.userService.addUser(data).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200) {
          this.showMessage.success = true;
          this.showMessage.error = false;
          this.showMessage.message = resp ? resp.body.message : '';
          this.resetForms();
        } else {
          this.showMessage.success = false;
          this.showMessage.error = true;
          this.showMessage.message = resp ? resp.body.message : '';
        }
      }, error => {
        this.showMessage.error = true;
        this.showMessage.success = false;
        this.showMessage.message = error ? error.error.message : '';
      });
    }
  }

  updateData(data: Staff) {
    this.userService.updateUser(data).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.showMessage.success = true;
        this.resetForms();
        this.onUserCreationSuccess.emit(this.showMessage.success);
        this.showMessage.error = false;
        this.showMessage.message = resp ? resp.body.message : '';
      } else {
        this.showMessage.success = false;
        this.showMessage.error = true;
        this.showMessage.message = resp ? resp.body.message : '';
      }
    }, error => {
      this.showMessage.error = true;
      this.showMessage.success = false;
      this.showMessage.message = error ? error.error.message : '';
    });
  }

  resetForms() {
    this.staffForm.reset();
    this.appAddress.resetForm();
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
      position: ['', Validators.required],
      occupation: ['staff'],
      gender: ['', Validators.required],
      picture: [''],
      userType: ['staff'],
      birthDate: ['', DateValidator('yyyy/MM/dd')],
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
      userType: 'staff',
    };
  }

  loadStaff(staffId: number) {
    this.userService.loadUser(staffId, 'staff').subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.staff = resp.body.data;
        this.staffForm.patchValue({
          id: this.staff.id,
          firstName: this.staff.firstName,
          lastName: this.staff.lastName,
          middleNames: this.staff.middleNames,
          occupation: this.staff.occupation,
          position: this.staff.position.id ,
          gender: this.staff.gender,
          phoneNumber: this.staff.phoneNumber,
          emailAddress: this.staff.emailAddress,
          picture: this.staff.picture,
          birthDate: DateFormatter(this.staff.birthDate, 'YYYY/MM/DD', false),
          userType: 'staff',
          fullName: this.staff.fullName,
          address: this.staff.address,
        });
        this.appAddress.patchAddressValue(this.staff.address);
      }
    }, error => {
      this.showMessage.error = true;
      this.showMessage.success = false;
      this.showMessage.message = error ? error.error.message : '';
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

  loadPositionList() {
    this.positionService.loadPositionList(this.getParam()).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const data = resp.body;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.showMessage.error = false;
        this.positions = data.data;
      } else {
        this.showMessage.success = false;
        this.showMessage.error = true;
        this.showMessage.message = data  ? data.message : '';
      }
    }, error => {
      this.showMessage.error = true;
      this.showMessage.success = false;
      this.showMessage.message = error ? error.error.message : '';
    });
  }
}
