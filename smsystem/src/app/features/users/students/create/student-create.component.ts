import { Component, OnInit, Input, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef, NbDateService } from '@nebular/theme';
import { Student, User } from 'app/models/users';
import { UserService } from 'app/services/users';
import { Position } from 'app/models/positions';
import { AcademicDiscipline } from 'app/models/academic-disciplines';
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
  @Input() title: string;
  @Output() onUserCreationSuccess = new EventEmitter();
  action: string = 'create';
  position: number;
  academicDiscipline: number;

  studentForm: FormGroup;
  parentForm: FormGroup;
  addressForm: FormGroup;
  student: Student;
  studentId: string;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  users: User[] = [];
  positions: Position[] = [];
  listDisplay: string = 'none';

  linearMode = true;

  toggleLinearMode() {
    this.linearMode = !this.linearMode;
  }

  constructor(
    protected positionService: PositionService,
    protected academicDisciplineService: AcademicDisciplineService,
    protected dateService: NbDateService<Date>,
    private userService:  UserService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<StudentCreateComponent>) {}

  ngOnInit() {
    this.loadStudentForm();
    this.loadParentForm();
    this.loadAddressForm();
  }
  ngAfterViewInit() {
    if (this.action === 'edit') {
      this.loadTeacher(parseInt(this.studentId, 10));
    }
  }
  dismiss() {
    this.ref.close();
  }
  onSubmit() {
    const dob = this.studentForm.get('birthDate');
    if (dob && dob.valid) {
      this.studentForm.patchValue({
        birthDate: DateFormatter(dob.value).format('YYYY/MM/DD'),
      });
    }
    this.student = this.studentForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updateTeacher();
    } else {
      this.userService.addUser(this.student).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200) {
          this.showMessage.success = true;
          this.studentForm.reset();
          this.ref.close();
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
  }
  updateTeacher() {
    this.userService.updateUser(this.student).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.showMessage.success = true;
        this.studentForm.reset();
        this.ref.close();
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
  search(value: string) {
    const param = this.getParam();
    param.search = value;
    this.userService.search(param).subscribe((result) => {
      this.users = [];
      if (result && result.success) {
        this.users = result.data;
        this.listDisplay = 'block';
      }
    });
  }

  loadStudentForm() {
    this.studentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      position: [''],
      occupation: ['student'],
      gender: ['', Validators.required],
      picture: [''],
      birthDate: ['', DateValidator('yyyy/MM/dd')],
      userType: ['student']
    });
  }
  loadParentForm() {
    this.parentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      occupation: ['parent'],
      gender: ['', Validators.required],
      picture: [''],
      userType: ['parent']
    });
  }

  loadAddressForm() {
    this.addressForm = this.formBuilder.group({
      id: [null],
      street: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      state: ['', Validators.required],
      postalCode: ['', Validators.required],
      unit: [null],
    });
  }

  pickUser(event: any, user: User) {
    event.preventDefault();
    const firstName = this.studentForm.get('firstName');
    const lastName = this.studentForm.get('lastName');

    this.listDisplay = 'none';
    this.studentForm.patchValue({
      userId: user.id,
      fullName: user.firstName + ' ' + user.lastName,
    });

    if (firstName && !firstName.value) {
      this.studentForm.patchValue({
        firstName: user.firstName,
      });
    }

    if (lastName && !lastName.value) {
      this.studentForm.patchValue({
        lastName: user.lastName,
      });
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
      userType: 'student',
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
}
