import { Component, OnInit, Input, Output, ViewChild, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDateService } from '@nebular/theme';
import { AddressComponent } from 'app/shared';
import { User, Position, AddressWrapper, AcademicDiscipline, ShowMessage  } from 'app/models';
import { UserService } from 'app/services/users';
import { PositionService } from 'app/services/positions';
import { AcademicDisciplineService } from 'app/services/academic-disciplines';
import { QueryParam , DateValidator, DateFormatter } from 'app/utils';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit, AfterViewInit {
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
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadPositionList();
    this.loadAcademicDisciplineList();
    this.loadForm();
    // this.loadUser();
  }
  ngAfterViewInit() {
    if (this.user) {
      this.loadUser();
    }
  }
  onSubmit() {
    this.showMessage.success = false;
    this.showMessage.error = false;
    this.updateUser(this.userForm.value);
  }
  updateUser(user: User) {
    this.userService.updateUser(user).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      window.console.log(resp);
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
  loadForm() {
    this.userForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      position: ['', Validators.required],
      occupation: ['User'],
      employmentCode: [''],
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

  loadUser() {
    this.position = this.user.position.id;
    this.academicDiscipline = this.user.academicDiscipline.id;
    this.userForm.patchValue({
      id: this.user.id,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      position: this.user.position.id ,
      occupation: this.user.occupation,
      employmentCode: this.user.userCode,
      gender: this.user.gender,
      picture: this.user.picture,
      userId: this.user.userId,
      middleNames: this.user.middleNames,
      phoneNumber: this.user.phoneNumber,
      emailAddress: this.user.emailAddress,
      birthDate: DateFormatter(this.user.birthDate, 'YYYY/MM/DD', false),
      userType: 'teacher',
      academicDiscipline: this.user.academicDiscipline.id,
      fullName: this.user.fullName,
      address: this.user.address,
    });
    if (this.appAddress) {
      this.appAddress.patchAddressValue(this.user.address);
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
  resetForms() {
    this.userForm.reset();
    this.appAddress.resetForm();
  }

}
