import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';

import { PageEvent } from '@angular/material';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { QueryParam } from 'app/utils';
import { UserService } from 'app/services/users';
import { UserDeleteComponent } from 'app/features/users/user-delete/user-delete.component';
import { Student } from 'app/models/users';

@Component({
  selector: 'app-parent-views',
  templateUrl: 'parents-view.component.html',
  styleUrls: ['parents-view.component.scss'],
})
export class ParentsViewComponent implements OnInit {
  title: string = 'Parent\'s List';
  baseURL: string = '/dashboard/users/students/';
  firstForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;

  hidePageSize: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'studentNumber', 'fullName', 'gender', 'emailAddress', 'phoneNumber', 'createdOn', 'action'];
  dataSource: MatTableDataSource<Student> = new MatTableDataSource<Student>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'student',
  };

  students: Array<Student> = [];


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
    this.router.navigate([this.baseURL + '/create']);
  }

  edit(student: Student) {
    this.router.navigate([this.baseURL + 'edit/' + student.id ]);
  }

  view(student: Student) {
    this.router.navigate([this.baseURL + '/' + student.id ]);
  }

  delete(student: Student) {
    this.dialogService.open(UserDeleteComponent, {
      context: {
        title: 'Delete Student',
        action: 'delete',
        userType: 'student',
        userId: student.id.toString(),
        name: student.fullName,
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
          this.dataSource = new MatTableDataSource<Student>(data.students);
        }
    });
  }
}