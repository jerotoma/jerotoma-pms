import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';

import { PageEvent } from '@angular/material';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { QueryParam } from 'app/utils';
import { UserService } from 'app/services/users';
import { TeacherCreateComponent } from './create/teacher-create.component';
import { UserDeleteComponent } from '../user-delete/user-delete.component';
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

  hidePageSize: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'gender', 'academicDiscipline', 'position', 'action'];
  dataSource: MatTableDataSource<Teacher> = new MatTableDataSource<Teacher>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  teachers: Array<Teacher> = [];


  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private dialogService: NbDialogService,
    ) {
  }

  ngOnInit() {
    this.loadUsers();
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

  edit(teacher: Teacher) {
    this.dialogService.open(TeacherCreateComponent, {
      context: {
        title: 'Edit Teacher',
        action: 'edit',
        teacherId: teacher.id.toString(),
      },
    }).onClose.subscribe(_data => {
      this.loadUsers();
    });
  }

  delete(teacher: Teacher) {
    this.dialogService.open(UserDeleteComponent, {
      context: {
        title: 'Delete Teacher',
        action: 'delete',
        userType: 'teacher',
        userId: teacher.id.toString(),
        name: teacher.fullName,
      },
    }).onClose.subscribe(_data => {
      this.loadUsers();
    });
  }

  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadUsers();
  }

  loadUsers() {
    this.userService.loadUsers(this.param).subscribe((result) => {
      const resp = result;
        const status = resp.status;
        if (status !== null && status === 200 && resp.body) {
          const data = resp.body.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<Teacher>(data.teachers);
        }
    });
  }
}
