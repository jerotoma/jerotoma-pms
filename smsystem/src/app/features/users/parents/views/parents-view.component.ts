import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';

import { PageEvent } from '@angular/material';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { UserDeleteComponent } from 'app/shared';
import { ParentCreateComponent } from '../create/parent-create.component';

import { QueryParam } from 'app/utils';
import { UserService } from 'app/services';
import { Parent } from 'app/models';

@Component({
  selector: 'app-parent-views',
  templateUrl: 'parents-view.component.html',
  styleUrls: ['parents-view.component.scss'],
})
export class ParentsViewComponent implements OnInit {
  title: string = 'Parent\'s List';
  baseURL: string = '/dashboard/users/parents/';
  firstForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;

  hidePageSize: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'gender', 'emailAddress', 'phoneNumber', 'createdOn', 'action'];
  dataSource: MatTableDataSource<Parent> = new MatTableDataSource<Parent>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'parent',
  };

  parents: Array<Parent> = [];


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
    this.dialogService.open(ParentCreateComponent, {
      context: {
        title: 'Add New Parent',
      },
    }).onClose.subscribe(data => {
      this.loadUsers();
    });
  }

  edit(parent: Parent) {
    this.router.navigate([this.baseURL + 'edit/' + parent.id ]);
  }

  view(parent: Parent) {
    this.router.navigate([this.baseURL + '/' + parent.id ]);
  }

  delete(parent: Parent) {
    this.dialogService.open(UserDeleteComponent, {
      context: {
        title: 'Delete Parent',
        action: 'delete',
        userType: 'parent',
        userId: parent.id.toString(),
        name: parent.fullName,
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
          this.dataSource = new MatTableDataSource<Parent>(data.parents);
        }
    });
  }
}
