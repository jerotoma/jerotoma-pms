import { Component, OnInit, Input, Output, ViewChild, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef, NbDateService } from '@nebular/theme';
import { AddressComponent, UserLoginInputComponent } from 'app/shared';

import {
  Teacher,
  User,
  Position,
  AddressWrapper,
  Department,
  ShowMessage,
  UserLoginInput,
  UserLoginInputWrapper,
  ResponseWrapper,
} from 'app/models';

import { PositionService , UserService, DepartmentService, ModalService } from 'app/services';
import { QueryParam , DateValidator, DateFormatter, APP_ACTION_TYPE, USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-teacher-create',
  templateUrl: 'teacher-create.component.html',
  styleUrls: ['teacher-create.component.scss'],
})
export class TeacherCreateComponent implements OnInit, AfterViewInit {
  @Input() title: string;
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  @ViewChild(UserLoginInputComponent, {static: false}) appUserLoginInput: UserLoginInputComponent;
  action: string = 'create';
  position: number;
  department: number;

  teacherForm: FormGroup;
  userLoginInput: UserLoginInput;
  teacher: Teacher;
  teacherId: string;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  users: User[] = [];
  positions: Position[] = [];
  departments: Department[] = [];
  listDisplay: string = 'none';

  constructor(
    protected positionService: PositionService,
    protected departmentService: DepartmentService,
    protected dateService: NbDateService<Date>,
    private userService:  UserService,
    private modalService: ModalService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<TeacherCreateComponent>) {}

  ngOnInit() {
    this.loadPositionList();
    this.loadDepartmentList();
    this.loadForm();
  }
  ngAfterViewInit() {
    if (this.action === APP_ACTION_TYPE.edit) {
      this.loadTeacher(parseInt(this.teacherId, 10));
    }
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.teacher = this.teacherForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === APP_ACTION_TYPE.edit) {
      this.updateTeacher();
    } else {
      this.userService.addUser(this.teacher).subscribe((resp: ResponseWrapper) => {
        if (resp && resp.success) {
          this.modalService.openSnackBar('New Teacher has been added', 'success');
          this.resetForms();
          this.appUserLoginInput.resetForm();
        }
      });
    }
  }
  updateTeacher() {
    this.userService.updateUser(this.teacher).subscribe((resp: ResponseWrapper) => {
      if (resp && resp.success) {
        this.modalService.openSnackBar('Teacher has been updated', 'success');
        this.resetForms();
      }
    });
  }


  loadForm() {
    this.teacherForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      position: ['', Validators.required],
      occupation: [USER_TYPE.teacher],
      userCode: [''],
      gender: ['', Validators.required],
      picture: [''],
      middleNames: [null],
      phoneNumber: ['', Validators.required],
      username: [null],
      password: [null],
      confirmPassword: [null],
      userId: [null],
      birthDate: ['', DateValidator('yyyy/MM/dd')],
      userType: [USER_TYPE.teacher],
      department: ['', Validators.required],
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
      userType: USER_TYPE.teacher,
    };
  }

  onUserSelected(teacher: Teacher) {
    this.teacher = teacher;
    this.appUserLoginInput.patchPasswordValue({email: teacher.username, password: '', confirmPassword: ''});
    this.updateUseInput();
  }

  loadTeacher(teacherId: number) {
    this.userService.loadUser(teacherId, USER_TYPE.teacher).subscribe((teacher: Teacher) => {
       if (teacher) {
        this.teacher = teacher;
        this.position = this.teacher.position.id;
        this.department = this.teacher.department.id;
        this.updateUseInput();
      }
    });
  }

  loadPositionList() {
    this.positionService.loadPositionList().subscribe((positions: Position[]) => {
      if (positions) {
        this.positions = positions;
      }
    });
  }

  loadDepartmentList() {
    this.departmentService.loadDepartmentList().subscribe((departments: Department[] ) => {
      if (departments) {
        this.showMessage.error = false;
        this.departments = departments;
      }
    });
  }

  onAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.teacherForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.teacherForm.controls['address'].setErrors(null);
      this.teacherForm.patchValue({address: addressWrapper.address});
    }
  }
  onUserLoginInputChange(userLoginInputWrapper: UserLoginInputWrapper) {
    if (userLoginInputWrapper.isValid) {
        this.userLoginInput = userLoginInputWrapper.userLoginInput;
        this.teacherForm.patchValue({
          username: this.userLoginInput.email,
          password: this.userLoginInput.password,
          confirmPassword: this.userLoginInput.confirmPassword,
        });
        this.teacherForm.controls['username'].setErrors(null);
    } else {
      this.teacherForm.controls['username'].setErrors({ invalidUsername: true });
    }
  }
  resetForms() {
    this.teacherForm.reset();
    this.appAddress.resetForm();
    this.ref.close();
  }

  updateUseInput() {
    this.teacherForm.patchValue({
      id: this.teacher.id,
      firstName: this.teacher.firstName,
      lastName: this.teacher.lastName,
      position: this.teacher.position ? this.teacher.position.id : null,
      occupation: this.teacher.occupation ? this.teacher.occupation : 'Teacher',
      userCode: this.teacher.userCode,
      gender: this.teacher.gender,
      picture: this.teacher.picture,
      userId: this.teacher.userId,
      middleNames: this.teacher.middleNames,
      phoneNumber: this.teacher.phoneNumber,
      emailAddress: this.teacher.username,
      birthDate: DateFormatter(this.teacher.birthDate, 'YYYY/MM/DD', false),
      userType: 'teacher',
      department: this.teacher.department ? this.teacher.department.id : null,
      address: this.teacher.address ? this.teacher.address : null,
    });

    if (this.teacher.address) {
      this.appAddress.patchAddressValue(this.teacher.address);
    }
  }

}
