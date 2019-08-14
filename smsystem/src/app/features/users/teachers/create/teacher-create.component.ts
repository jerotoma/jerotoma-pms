import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef, NbDateService } from '@nebular/theme';
import { Teacher, User } from 'app/models/users';
import { UserService } from 'app/services/users';
import { Position } from 'app/models/positions/position.model';
import { PositionService } from 'app/services/positions';
import { QueryParam , DateValidator, DateFormatter} from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-teacher-create',
  templateUrl: 'teacher-create.component.html',
  styleUrls: ['teacher-create.component.scss'],
})
export class TeacherCreateComponent implements OnInit {
  @Input() title: string;
  @Output() onUserCreationSuccess = new EventEmitter();

  teacherForm: FormGroup;
  teacher: Teacher;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  users: User[] = [];
  positions: Position[] = [];

  listDisplay: string = 'none';

  constructor(
    protected positionService: PositionService,
    protected dateService: NbDateService<Date>,
    private userService:  UserService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<TeacherCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.onCredentialInputChanges();
    this.loadPositionList();
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
    const dob = this.teacherForm.get('birthDate');
    if (dob && dob.valid) {
      this.teacherForm.patchValue({
        birthDate: DateFormatter(dob.value).format('YYYY/MM/DD'),
      });
    }
    this.teacher = this.teacherForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    this.userService.addUser(this.teacher).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
    const resp = result;
    const status = resp.status;
    if (status !== null && status === 200) {
      this.showMessage.success = true;
      this.teacherForm.reset();
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

  loadForm() {
    this.teacherForm = this.formBuilder.group({
      id: [''],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      position: ['', Validators.required],
      occupation: ['Teacher'],
      employmentCode: [''],
      gender: ['', Validators.required],
      picture: [''],
      userId: ['', Validators.required],
      birthDate: ['', DateValidator('yyyy/MM/dd')],
      userType: ['teacher'],
      fieldOfStudy: ['', Validators.required],
      fullName: ['', Validators.required],
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

  loadPositionList(){
    this.positionService.loadPositionList(this.getParam()).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const data = resp.body;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.showMessage.success = true;
        this.showMessage.error = false;
        this.showMessage.message = data  ? data.message : '';
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
