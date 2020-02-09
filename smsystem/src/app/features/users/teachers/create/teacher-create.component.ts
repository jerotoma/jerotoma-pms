import { Component, OnInit, Input, Output, ViewChild, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef, NbDateService } from '@nebular/theme';
import { AddressComponent } from 'app/shared';
import { Teacher, User, Position, AddressWrapper, AcademicDiscipline, ShowMessage  } from 'app/models';
import { UserService } from 'app/services/users';
import { PositionService } from 'app/services/positions';
import { AcademicDisciplineService } from 'app/services/academic-disciplines';
import { QueryParam , DateValidator, DateFormatter } from 'app/utils';

@Component({
  selector: 'app-teacher-create',
  templateUrl: 'teacher-create.component.html',
  styleUrls: ['teacher-create.component.scss'],
})
export class TeacherCreateComponent implements OnInit, AfterViewInit {
  @Input() title: string;
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  action: string = 'create';
  position: number;
  academicDiscipline: number;

  teacherForm: FormGroup;
  teacher: Teacher;
  teacherId: string;
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
    protected ref: NbDialogRef<TeacherCreateComponent>) {}

  ngOnInit() {
    this.loadPositionList();
    this.loadAcademicDisciplineList();
    this.loadForm();
    this.onCredentialInputChanges();
  }
  ngAfterViewInit() {
    if (this.action === 'edit') {
      this.loadTeacher(parseInt(this.teacherId, 10));
    }
  }
  dismiss() {
    this.ref.close();
  }
  onCredentialInputChanges() {
    this.teacherForm.get('fullName').valueChanges.subscribe(value => {
      this.search(value);
    });
  }
  onSubmit() {
    this.teacher = this.teacherForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updateTeacher();
    } else {
      this.userService.addUser(this.teacher).subscribe((teacher: Teacher) => {
       this.teacher = teacher;
      });
    }
  }
  updateTeacher() {
    this.userService.updateUser(this.teacher).subscribe((teacher: Teacher) => {
      this.teacher = teacher;
    });
  }
  search(value: string) {
    const param = this.getParam();
    param.search = value;
    this.userService.search(param).subscribe((users: User[]) => {
      this.users = [];
      if (users) {
        this.users = users;
        this.listDisplay = 'block';
      }
    });
  }

  loadForm() {
    this.teacherForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      position: ['', Validators.required],
      occupation: ['Teacher'],
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

  pickUser(event: any, user: User) {
    event.preventDefault();
    const firstName = this.teacherForm.get('firstName');
    const lastName = this.teacherForm.get('lastName');

    this.listDisplay = 'none';
    this.teacherForm.patchValue({
      userId: user.id,
      fullName: user.firstName + ' ' + user.lastName,
    });

    if (firstName && !firstName.value) {
      this.teacherForm.patchValue({
        firstName: user.firstName,
      });
    }

    if (lastName && !lastName.value) {
      this.teacherForm.patchValue({
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
      userType: 'teacher',
    };
  }

  loadTeacher(teacherId: number) {
    this.userService.loadUser(teacherId, 'teacher').subscribe((teacher: Teacher) => {
       if (teacher) {
        this.teacher = teacher;
        this.position = this.teacher.position.id;
        this.academicDiscipline = this.teacher.academicDiscipline.id;
        this.teacherForm.patchValue({
          id: this.teacher.id,
          firstName: this.teacher.firstName,
          lastName: this.teacher.lastName,
          position: this.teacher.position.id ,
          occupation: this.teacher.occupation,
          employmentCode: this.teacher.teacherCode,
          gender: this.teacher.gender,
          picture: this.teacher.picture,
          userId: this.teacher.userId,
          middleNames: this.teacher.middleNames,
          phoneNumber: this.teacher.phoneNumber,
          emailAddress: this.teacher.emailAddress,
          birthDate: DateFormatter(this.teacher.birthDate, 'YYYY/MM/DD', false),
          userType: 'teacher',
          academicDiscipline: this.teacher.academicDiscipline.id,
          fullName: this.teacher.fullName,
          address: this.teacher.address,
        });
        this.appAddress.patchAddressValue(this.teacher.address);
      }
    });
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
      this.teacherForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.teacherForm.controls['address'].setErrors(null);
      this.teacherForm.patchValue({address: addressWrapper.address});

    }
  }

  resetForms() {
    this.teacherForm.reset();
    this.appAddress.resetForm();
    this.ref.close();
  }

}
