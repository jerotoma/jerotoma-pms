import { Component, OnInit, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';

import { QueryParam } from 'app/utils';
import { UserService } from 'app/services/users';
import { TeacherCreateComponent } from './create/teacher-create.component';
import { Teacher } from 'app/models/users';


@Component({
  selector: 'app-teacher',
  templateUrl: 'teachers.component.html',
  styleUrls: ['teachers.component.scss'],
})
export class TeachersComponent implements OnInit {
  title: string = 'Teacher\'s List';
  firstForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;

  columns: string[] = [
    'ID',
    'Full Name',
    'Gender',
    'Occupation',
    'Position',
    '# Of Courses',
    'Action',
  ];

  teachers: Array<Teacher> = [];


  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private dialogService: NbDialogService,
    ) {
  }

  ngOnInit() {
    this.firstForm = this.fb.group({
      firstCtrl: ['', Validators.required],
    });

    this.secondForm = this.fb.group({
      secondCtrl: ['', Validators.required],
    });

    this.thirdForm = this.fb.group({
      thirdCtrl: ['', Validators.required],
    });
    this.loadUsers();
  }

  onFirstSubmit() {
    this.firstForm.markAsDirty();
  }

  onSecondSubmit() {
    this.secondForm.markAsDirty();
  }

  onThirdSubmit() {
    this.thirdForm.markAsDirty();
  }

  open() {
    this.dialogService.open(TeacherCreateComponent, {
      context: {
        title: 'Add New Teacher',
      },
    }).onClose.subscribe(data => {
      this.loadUsers();
    });
  }
  loadUsers() {
    this.userService.loadTeachers(this.getParam()).subscribe((result) => {
      if (result.success && result.data) {
          this.teachers = result.data;
      }
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
      userType: 'teacher',
    };
  }
}
