import { Component, OnInit, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbStepperComponent } from '@nebular/theme';
import { AddressComponent } from 'app/shared';
import { Student, Parent } from 'app/models/users';
import { Address, AddressWrapper } from 'app/models/addresses';
import { UserService } from 'app/services/users';
import { PositionService } from 'app/services/positions';
import { AcademicDisciplineService } from 'app/services/academic-disciplines';
import { QueryParam , DateValidator, DateFormatter } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';


@Component({
  selector: 'app-student-create',
  templateUrl: 'student-create.component.html',
  styleUrls: ['student-create.component.scss'],
})
export class StudentCreateComponent implements OnInit, AfterViewInit {
  title: string = 'Create New Student';
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild('stepper', {static: true}) stepper: NbStepperComponent;
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  action: string = 'create';

  studentForm: FormGroup;
  parentForm: FormGroup;
  addressForm: FormGroup;
  student: Student;
  address: Address;
  parent: Parent;
  studentId: string;
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
    protected academicDisciplineService: AcademicDisciplineService,
    private userService:  UserService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadStudentForm();
    this.loadParentForm();
  }
  ngAfterViewInit() {
    if (this.action === 'edit') {
      this.loadTeacher(parseInt(this.studentId, 10));
    }
  }
  dismiss() {

  }

  onStudentSubmit() {
    this.title = 'Create New Student';
  }

  onParentSubmit() {
    this.title = 'Create New Parent';
  }


  onSubmit() {
    window.console.log(this.studentForm, this.parentForm);
   if (this.studentForm.valid && this.parentForm.valid ){
    const dob = this.studentForm.get('birthDate');
    if (dob && dob.valid) {
      this.studentForm.patchValue({
        birthDate: DateFormatter(dob.value).format('YYYY/MM/DD'),
      });
    }
    const data = {
        student: this.studentForm.value,
        parent: this.parentForm.value,
        userType: 'studentAndParent',
      }
      this.postData(data);
    }
  }

  postData(data: any) {
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
  updateData(data: any) {
    this.userService.updateUser(data).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.showMessage.success = true;
        this.studentForm.reset();
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
    this.studentForm.reset();
    this.parentForm.reset();
    this.appAddress.resetForm();
    this.stepper.reset();
  }

  loadStudentForm() {
    this.studentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      middleNames: [''],
      phoneNumber: ['', Validators.required],
      emailAddress: [null],
      gender: ['', Validators.required],
      picture: [''],
      userType: ['student'],
      birthDate: ['', DateValidator('yyyy/MM/dd')],
      address: [null, Validators.required],
    });
  }

  loadParentForm() {
    this.parentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      middleNames: [''],
      occupation: [''],
      phoneNumber: ['', Validators.required],
      emailAddress: [null],
      gender: ['', Validators.required],
      picture: [''],
      userType: ['parent'],
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
      userType: 'studentAndParent',
    };
  }

  loadTeacher(studentId: number) {
    this.userService.loadUser(studentId, 'student').subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.student = resp.body.data;
        this.studentForm.patchValue({
          id: this.student.id,
          firstName: this.student.firstName,
          lastName: this.student.lastName,
          occupation: this.student.occupation,
          gender: this.student.gender,
          picture: this.student.picture,
          birthDate: DateFormatter(this.student.birthDate, 'YYYY/MM/DD', false),
          userType: 'student',
          fullName: this.student.fullName,
        });
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

  onParentAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.parentForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.parentForm.controls['address'].setErrors(null);
      this.parentForm.patchValue({address: addressWrapper.address});
    }
  }
}
