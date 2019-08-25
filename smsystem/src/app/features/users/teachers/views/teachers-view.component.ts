import { Component, OnInit, ViewChild } from '@angular/core';
import { NbDialogService } from '@nebular/theme';

import { PageEvent } from '@angular/material';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { QueryParam } from 'app/utils';
import { UserService } from 'app/services/users';
import { TeacherCreateComponent } from '../create/teacher-create.component';
import { UserDeleteComponent } from 'app/features/users/user-delete/user-delete.component';
import { Teacher } from 'app/models/users';

@Component({
  selector: 'app-teachers-view',
  templateUrl: 'teachers-view.component.html',
  styleUrls: ['teachers-view.component.scss'],
})
export class TeachersViewComponent implements OnInit {
  title: string = 'Teacher\'s List';

  hidePageSize: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'gender', 'emailAddress', 'phoneNumber', 'academicDiscipline', 'position', 'action'];
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
