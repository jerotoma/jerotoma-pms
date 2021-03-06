import { Component, OnInit, ViewChild } from '@angular/core';
import { NbDialogService } from '@nebular/theme';
import { Router } from '@angular/router';

import { PageEvent } from '@angular/material/paginator';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { QueryParam } from 'app/utils';
import { UserService } from 'app/services/users';
import { TeacherCreateComponent } from '../create/teacher-create.component';
import { UserDeleteComponent } from 'app/shared';
import { Teacher } from 'app/models/users';

@Component({
  selector: 'app-teachers-view',
  templateUrl: 'teachers-view.component.html',
  styleUrls: ['teachers-view.component.scss'],
})
export class TeachersViewComponent implements OnInit {
  title: string = 'Teacher\'s List';
  baseURL: string = '/dashboard/users/teachers/';
  hidePageSize: boolean = true;
  isLoading: boolean = false;
  totalNumberOfItems: number = 0;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'gender', 'emailAddress', 'phoneNumber', 'department', 'position', 'action'];
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
    private dialogService: NbDialogService,
    private router: Router,
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
        userId: teacher.userId,
      },
    }).onClose.subscribe(_data => {
      this.loadUsers();
    });
  }

  view(teacher: Teacher) {
    this.router.navigate([this.baseURL + '/' + teacher.userId ]);
  }

  delete(teacher: Teacher) {
    this.dialogService.open(UserDeleteComponent, {
      context: {
        title: 'Delete Teacher',
        action: 'delete',
        userType: 'teacher',
        userId: teacher.userId,
        name: teacher.fullName,
      },
    }).onClose.subscribe(_data => {
      this.loadUsers();
    });
  }

  assignClassesToTeacher(teacher: Teacher) {

  }

  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadUsers();
  }

  loadUsers() {
    this.isLoading = true;
    this.userService.loadUsers(this.param).subscribe((result: any) => {
      this.isLoading = false;
      if (result) {
        this.hidePageSize = result.teachers.length < 10;
        this.totalNumberOfItems = result.count;
        this.dataSource = new MatTableDataSource<Teacher>(result.teachers);
      }
    });
  }
}
