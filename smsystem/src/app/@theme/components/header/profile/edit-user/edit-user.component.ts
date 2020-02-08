import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDateService } from '@nebular/theme';
import { AddressComponent } from 'app/shared';
import { User, Position, AddressWrapper, AcademicDiscipline, ShowMessage  } from 'app/models';
import {
  UserService,
  PositionService,
  AcademicDisciplineService,
  ModalService,
} from 'app/services';

import { QueryParam , DateValidator, DateFormatter } from 'app/utils';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {
  @Input('userType') userType: string = 'teacher';
  @Input('user') user: User = null;
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  action: string = 'edit';
  position: number;
  academicDiscipline: number;

  userForm: FormGroup;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  users: User[] = [];
  positions: Position[] = [];
  academicDisciplines: AcademicDiscipline[] = [];
  listDisplay: string = 'none';

  constructor(
    protected positionService: PositionService,
    protected academicDisciplineService: AcademicDisciplineService,
    protected dateService: NbDateService<Date>,
    private userService:  UserService,
    private modalService: ModalService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadPositionList();
    this.loadAcademicDisciplineList();
    this.loadForm();
    if (this.user) {
      this.loadUser(this.user);
    }
  }

  onSubmit() {
    this.showMessage.success = false;
    this.showMessage.error = false;
    this.updateUser(this.userForm.value);
  }
  updateUser(data: User) {
    this.userService.updateUser(data).subscribe((user: User) => {
      if (user) {
        this.showMessage.success = true;
        this.onUserCreationSuccess.emit(this.showMessage.success);
        this.showMessage.error = false;
        this.openSnackBar('User has been updated', 'success');
      }
    });
  }

  loadForm() {
    this.userForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      position: ['', Validators.required],
      occupation: ['User'],
      userCode: [''],
      gender: ['', Validators.required],
      picture: [''],
      middleNames: [null],
      phoneNumber: ['', Validators.required],
      emailAddress: [null],
      userId: [null, Validators.required],
      birthDate: ['', DateValidator('yyyy/MM/dd')],
      userType: ['teacher'],
      academicDiscipline: ['', Validators.required],
      fullName: ['', Validators.required],
      address: [null, Validators.required],
    });
  }

  loadUser(user: User) {
    this.position = user.position.id;
    this.academicDiscipline = user.academicDiscipline.id;
    this.userForm.patchValue({
      id: user.id,
      firstName: user.firstName,
      lastName: user.lastName,
      position: user.position.id ,
      occupation: user.occupation,
      userCode: user.userCode,
      gender: user.gender,
      picture: user.picture,
      userId: user.userId,
      middleNames: user.middleNames,
      phoneNumber: user.phoneNumber,
      emailAddress: user.emailAddress,
      birthDate: DateFormatter(user.birthDate, 'YYYY/MM/DD', false),
      userType: 'teacher',
      academicDiscipline: user.academicDiscipline.id,
      fullName: user.fullName,
      address: user.address,
    });
    if (this.appAddress) {
      this.appAddress.patchAddressValue(user.address);
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

  loadAcademicDisciplineList() {
    this.academicDisciplineService.loadAcademicDisciplineList(this.getParam()).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const data = resp.body;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.showMessage.error = false;
        this.academicDisciplines = data.data;
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

  onAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.userForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.userForm.controls['address'].setErrors(null);
      this.userForm.patchValue({address: addressWrapper.address});

    }
  }
  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'parent',
    };
  }

  openSnackBar(message: string, panelClass: string) {
    this.modalService.openSnackBar(message, panelClass);
  }
}
