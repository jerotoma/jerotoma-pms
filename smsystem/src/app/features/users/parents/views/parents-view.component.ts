import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';

import { PageEvent } from '@angular/material/paginator';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { UserDeleteComponent } from 'app/shared';
import { ParentCreateComponent } from '../create/parent-create.component';

import { QueryParam, APP_ACTION_TYPE } from 'app/utils';
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

  hidePageSize: boolean = true;
  isLoading: boolean = false;
  totalNumberOfItems: number = 0;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'gender', 'emailAddress', 'phoneNumber', 'createdOn', 'action'];
  dataSource: MatTableDataSource<Parent> = new MatTableDataSource<Parent>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

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
        action: 'create',
      },
    }).onClose.subscribe(data => {
      this.loadUsers();
    });
  }

  edit(parent: Parent) {
    this.dialogService.open(ParentCreateComponent, {
      context: {
        title: 'Edit ' + parent.fullName + '\'s Details',
        action: APP_ACTION_TYPE.edit,
        parentId: parent.id,
      },
    }).onClose.subscribe(data => {
      this.loadUsers();
    });
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
        userId: parent.id,
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
    this.isLoading = true;
    this.userService.loadUsers(this.param).subscribe((result) => {
        this.isLoading = false;
        if (result) {
          this.totalNumberOfItems = result.count;
          this.dataSource = new MatTableDataSource<Parent>(result.parents);
          this.dataSource.sort = this.sort;
        }
    });
  }
}
