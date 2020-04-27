import { Component, OnInit, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { AddressComponent, UserLoginInputComponent } from 'app/shared';
import { Student, Parent } from 'app/models/users';
import { Address, AddressWrapper, UserLoginInput, UserLoginInputWrapper, ResponseWrapper } from 'app/models';
import { PositionService, UserService, ModalService } from 'app/services';
import { AcademicDisciplineService } from 'app/services/academic-disciplines';
import { QueryParam , DateValidator, DateFormatter, USER_TYPE } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';


@Component({
  selector: 'app-student-create',
  templateUrl: 'student-create.component.html',
  styleUrls: ['student-create.component.scss'],
})
export class StudentCreateComponent implements OnInit, AfterViewInit {
  title: string = 'Create New Student';
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  @ViewChild(UserLoginInputComponent, {static: false}) appPassword: UserLoginInputComponent;
  action: string = 'create';

  studentForm: FormGroup;
  addressForm: FormGroup;
  userLoginInput: UserLoginInput;
  student: Student;
  address: Address;
  parent: Parent;
  parents: Parent[] = [];
  selectedParents: Parent[] = [];
  parentIds: number[]  = [];
  studentId: string;
  parentFullName: string;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    protected positionService: PositionService,
    protected academicDisciplineService: AcademicDisciplineService,
    protected ref: NbDialogRef<StudentCreateComponent>,
    private userService:  UserService,
    private modalService: ModalService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadStudentForm();
    this.onCredentialInputChanges();
  }
  ngAfterViewInit() {
    if (this.action === 'edit') {
      this.loadStudent(parseInt(this.studentId, 10));
    }
  }
  dismiss() {
    this.ref.close();
  }
  onSubmit() {
    this.parentIds = [];
   if (!this.studentForm.valid ) {
      return;
    }
    this.student = this.studentForm.value;
    for (let i = 0; i < this.selectedParents.length; i++) {
      this.parentIds.push(this.selectedParents[i].id);
    }
    this.student.parentIds = this.parentIds;
    if (this.action === 'edit') {
      this.updateData(this.student);
    } else {
      this.postData(this.student);
    }
  }

  postData(data: Student) {
      this.userService.addUser(data).subscribe((resp: ResponseWrapper) => {
        if (resp && resp.success) {
          this.modalService.openSnackBar('New Student has been created', 'success');
          this.resetForms();
        }
      });
  }
  updateData(data: Student) {
      this.userService.updateUser(data).subscribe((resp: ResponseWrapper) => {
        if (resp && resp.success) {
          this.modalService.openSnackBar('Student has been updated', 'success');
          this.resetForms();
        }
    });
  }

  resetForms() {
    this.studentForm.reset();
    this.appAddress.resetForm();
    this.appPassword.resetForm();
    this.dismiss();
  }

  loadStudentForm() {
    this.studentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      studentNumber: [null],
      middleNames: [null],
      phoneNumber: ['', Validators.required],
      username: [null],
      password: [null],
      confirmPassword: [null],
      gender: ['', Validators.required],
      picture: [''],
      userType: [USER_TYPE.student],
      birthDate: ['', DateValidator('yyyy/MM/dd')],
      address: [null, Validators.required],
      parentFullName: [''],
      selectedParents: [''],
      userId: [null],
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
      userType: USER_TYPE.student,
    };
  }

  loadStudent(studentId: number) {
    this.userService.loadUser(studentId, USER_TYPE.student).subscribe((student: Student) => {
       if (student) {
        this.student = student;
        this.updateUseInput();
      }
    }, error => {
      this.showMessage.error = true;
      this.showMessage.success = false;
      this.showMessage.message = error ? error.error.message : '';
    });
  }

  onStudentAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.studentForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.studentForm.controls['address'].setErrors(null);
      this.studentForm.patchValue({address: addressWrapper.address});

    }
  }
  onUserLoginInputChange(userLoginInputWrapper: UserLoginInputWrapper) {
    if (userLoginInputWrapper.isValid) {
        this.userLoginInput = userLoginInputWrapper.userLoginInput;
        this.studentForm.patchValue({
          username: this.userLoginInput.email,
          password: this.userLoginInput.password,
          confirmPassword: this.userLoginInput.confirmPassword,
        });
        this.studentForm.controls['username'].setErrors(null);
        window.console.log(userLoginInputWrapper);
    } else {
      this.studentForm.controls['username'].setErrors({ invalidUsername: true });
    }
  }

  pickUser(event: any, parent: Parent) {
    event.preventDefault();
    let parentFound = false;
    this.listDisplay = 'none';
    if (this.selectedParents.length === 0) {
      this.selectedParents.push(parent);
    } else {
        this.selectedParents.forEach(p => {
          if (p.id === parent.id) {
            parentFound = true;
          }
      });
      if (!parentFound) {
        this.selectedParents.push(parent);
      }
    }
    this.studentForm.patchValue({
      selectedParents: this.parentIds,
      parentFullName: '',
    });
  }
  search(value: string) {
    const param = this.getParam();
    param.search = value;
    this.userService.search(param).subscribe((users: Parent[]) => {
      this.parents = [];
      if (users) {
        this.parents = users;
        this.listDisplay = 'block';
      }
    });
  }
  onCredentialInputChanges() {
    this.studentForm.get('parentFullName').valueChanges.subscribe(value => {
      if (value) {
        this.search(value);
      }
    });
  }

  selectedParentChanged(event: any, parent: Parent) {
    if (!event) {
      for (let i = 0; i < this.selectedParents.length; i++) {
        if (this.selectedParents[i].id === parent.id) {
          this.selectedParents.splice(i, 1);
        }
     }
    } else {
      this.selectedParents.push(parent);
    }
  }

  updateUseInput() {
    this.studentForm.patchValue({
      id: this.student.id,
      firstName: this.student.firstName,
      lastName: this.student.lastName,
      position: this.student.position ? this.student.position.id : null,
      occupation: this.student.occupation ? this.student.occupation : 'Student',
      employmentCode: this.student.userCode,
      gender: this.student.gender,
      picture: this.student.picture,
      userId: this.student.userId,
      middleNames: this.student.middleNames,
      phoneNumber: this.student.phoneNumber,
      emailAddress: this.student.username,
      birthDate: DateFormatter(this.student.birthDate, 'YYYY/MM/DD', false),
      userType: USER_TYPE.student,
      academicDiscipline: this.student.academicDiscipline ? this.student.academicDiscipline.id : null,
      fullName: this.student.fullName,
      address: this.student.address ? this.student.address : null,
    });

    if (this.student.address) {
      this.appAddress.patchAddressValue(this.student.address);
    }
  }
}
