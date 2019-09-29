import { Component, OnInit, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef, NbDateService } from '@nebular/theme';
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
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  action: string = 'create';

  studentForm: FormGroup;
  parentForm: FormGroup;
  addressForm: FormGroup;
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
  updateData(data: Student) {
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
    this.studentForm.reset();
    this.appAddress.resetForm();
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
      emailAddress: [null],
      gender: ['', Validators.required],
      picture: [''],
      userType: ['student'],
      birthDate: ['', DateValidator('yyyy/MM/dd')],
      address: [null, Validators.required],
      parentFullName: [''],
      selectedParents: [''],
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
      userType: 'parent',
    };
  }

  loadStudent(studentId: number) {
    this.userService.loadUser(studentId, 'student').subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.student = resp.body.data;
        this.address = this.student.address,
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
          emailAddress: this.student.emailAddress,
          middleNames: this.student.middleNames,
          phoneNumber: this.student.phoneNumber,
          studentNumber: this.student.studentNumber,
          address: this.student.address,
        });
        this.appAddress.patchAddressValue(this.address);
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
    this.userService.search(param).subscribe((result) => {
      this.parents = [];
      if (result && result.success) {
        this.parents = result.data;
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
}
