import { Component, OnInit, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { AddressComponent, UserLoginInputComponent } from 'app/shared';
import {
  Student,
  Address,
  AddressWrapper,
  Parent,
  UserLoginInput,
  UserLoginInputWrapper,
  ResponseWrapper,
} from 'app/models';
import { AcademicDisciplineService, ModalService , PositionService, UserService} from 'app/services';
import { QueryParam , DateFormatter, USER_TYPE, APP_ACTION_TYPE } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';


@Component({
  selector: 'app-parent-create',
  templateUrl: 'parent-create.component.html',
  styleUrls: ['parent-create.component.scss'],
})
export class ParentCreateComponent implements OnInit, AfterViewInit {
  title: string = 'Create New Parent';
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  @ViewChild(UserLoginInputComponent, {static: false}) appPassword: UserLoginInputComponent;
  action: string = 'create';

  parentForm: FormGroup;
  addressForm: FormGroup;
  student: Student;
  address: Address;
  userLoginInput: UserLoginInput;
  students: Student[] = [];
  selectedStudents: Student[] = [];
  studentIds: number[]  = [];
  parent: Parent;
  parentId: number;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    protected positionService: PositionService,
    protected academicDisciplineService: AcademicDisciplineService,
    protected ref: NbDialogRef<ParentCreateComponent>,
    private userService:  UserService,
    private modalService: ModalService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadParentForm();
    this.onStudentFullInputChanges();
  }
  ngAfterViewInit() {
    if (this.action === APP_ACTION_TYPE.edit) {
      this.loadParent(this.parentId);
    }
  }
  dismiss() {
    this.ref.close();
  }
  onSubmit() {
    this.studentIds = [];
    // window.console.log(this.parentForm, this.parentForm);
   if (this.parentForm.valid ) {
      this.parent = this.parentForm.value;
      for (let i = 0; i < this.selectedStudents.length; i++) {
        this.studentIds.push(this.selectedStudents[i].id);
      }
      this.parent.studentIds = this.studentIds;
      this.postData(this.parent);
    }
  }

  postData(data: Parent) {
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === APP_ACTION_TYPE.edit) {
      this.updateData(data);
    } else {
      this.userService.addUser(data).subscribe((resp: ResponseWrapper ) => {
        if (resp && resp.success) {
          this.modalService.openSnackBar('New Parent has been created', 'success');
          this.resetForms();
        }
      });
    }
  }
  updateData(data: Parent) {
    this.userService.updateUser(data).subscribe((resp: ResponseWrapper) => {
      if (resp && resp.success) {
        this.modalService.openSnackBar('Parent has been updated', 'success');
        this.resetForms();
      }
    });
  }

  loadParent(parentId: number) {
    this.userService.loadUser(parentId, USER_TYPE.PARENT).subscribe((parent: Parent) => {
      if (parent) {
        this.parent = parent;
        this.updateUseInput();
      }
    });
  }

  resetForms() {
    this.parentForm.reset();
    this.appAddress.resetForm();
    if (this.action === APP_ACTION_TYPE.create) {
      this.appPassword.resetForm();
    }
    this.ref.close();
  }
  loadParentForm() {
    this.parentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      middleNames: [null],
      occupation: [null],
      phoneNumber: [null, Validators.required],
      username: [null],
      password: [null],
      confirmPassword: [null],
      gender: ['', Validators.required],
      userId: [null],
      picture: [''],
      userType: [USER_TYPE.PARENT],
      address: [null, Validators.required],
      studentIDs: [''],
      studentFullName: [''],
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
      userType: USER_TYPE.PARENT,
    };
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
        this.userLoginInput = userLoginInputWrapper.userLoginInput;
        this.parentForm.patchValue({
          username: this.userLoginInput.email,
          password: this.userLoginInput.password,
          confirmPassword: this.userLoginInput.confirmPassword,
        });
        this.parentForm.controls['username'].setErrors(null);
        // window.console.log(userLoginInputWrapper);
    } else {
      this.parentForm.controls['username'].setErrors({ invalidUsername: true });
    }
  }

  pickUser(event: any, student: Student) {
    event.preventDefault();
    this.listDisplay = 'none';
    let studentFound = false;
    if (this.selectedStudents.length === 0) {
      this.selectedStudents.push(student);
    } else {
      this.selectedStudents.forEach(p => {
          if (p.id === student.id) {
           studentFound = true;
          }
      });
      if (!studentFound) {
        this.selectedStudents.push(student);
      }
    }
    this.parentForm.patchValue({
      studentIds: this.studentIds,
      studentFullName: '',
    });
  }
  search(value: string) {
    const param = this.getParam();
    param.userType = 'student';
    param.search = value;
    this.userService.search(param).subscribe((students: Student[]) => {
      this.students = [];
      if (students) {
        this.students = students;
        this.listDisplay = 'block';
      }
    });
  }
  onStudentFullInputChanges() {
    this.parentForm.get('studentFullName').valueChanges.subscribe(value => {
      if (value) {
        this.search(value);
      }
    });
  }

  selectedParentChanged(event: any, student: Student) {
    if (!event) {
      for (let i = 0; i < this.selectedStudents.length; i++) {
        if (this.selectedStudents[i].id === student.id) {
          this.selectedStudents.splice(i, 1);
        }
     }
    } else {
      this.selectedStudents.push(student);
    }
  }

  updateUseInput() {
    this.parentForm.patchValue({
      id: this.parent.id,
      firstName: this.parent.firstName,
      lastName: this.parent.lastName,
      position: this.parent.position ? this.parent.position.id : null,
      occupation: this.parent.occupation ? this.parent.occupation : 'Parent',
      employmentCode: this.parent.userCode,
      gender: this.parent.gender,
      picture: this.parent.picture,
      userId: this.parent.userId,
      middleNames: this.parent.middleNames,
      phoneNumber: this.parent.phoneNumber,
      emailAddress: this.parent.username,
      birthDate: DateFormatter(this.parent.birthDate, 'YYYY/MM/DD', false),
      userType: USER_TYPE.PARENT,
      academicDiscipline: this.parent.academicDiscipline ? this.parent.academicDiscipline.id : null,
      fullName: this.parent.fullName,
      address: this.parent.address ? this.parent.address : null,
    });

    if (this.parent.address) {
      this.appAddress.patchAddressValue(this.parent.address);
    }
  }
}
