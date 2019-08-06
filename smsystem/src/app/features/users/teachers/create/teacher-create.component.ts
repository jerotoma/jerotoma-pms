
import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef, NbDateService } from '@nebular/theme';
import { Teacher, User } from '../../../../models/users';
import { UserService } from '../../../../services/users';
import { QueryParam } from './../../../../utils';

@Component({
  selector: 'app-teacher-create',
  templateUrl: 'teacher-create.component.html',
  styleUrls: ['teacher-create.component.scss'],
})
export class TeacherCreateComponent implements OnInit {
  @Input() title: string;
  teacherForm: FormGroup;
  teacher: Teacher;
  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
  };
  users: User[] = [];

  listDisplay: string = 'none';

  constructor(
    protected dateService: NbDateService<Date>,
    private userService:  UserService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<TeacherCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.onCredentialInputChanges();
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
   this.userService.addUser(this.teacher).subscribe((result) => {
     console.log(result);
   });
  }
  loadUsers(){
    this.userService.load(this.param).subscribe((result) => {
      console.log(result);
    });
  }

  search(value: string) {
    this.param.search = value;
    this.userService.search(this.param).subscribe((result) => {
      this.users = [];
      if (result && result.success){
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
      teacherCode: [''],
      gender: ['', Validators.required],
      picture: [''],
      userId: ['', Validators.required],
      birthDate: [''],
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

}
