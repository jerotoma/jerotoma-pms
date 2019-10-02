import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { StaffCreateComponent } from '../create/staff-create.component';

import { PageEvent } from '@angular/material';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { QueryParam } from 'app/utils';
import { UserService } from 'app/services/users';
import { PositionService } from 'app/services/positions';
import { UserDeleteComponent } from 'app/shared';
import { Staff, ShowMessage, Position } from 'app/models';

@Component({
  selector: 'app-staffs-view',
  templateUrl: 'staffs-view.component.html',
  styleUrls: ['staffs-view.component.scss'],
})
export class StaffsViewComponent implements OnInit {
  title: string = 'Staff\'s List';
  baseURL: string = '/dashboard/users/staffs/';
  firstForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;

  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'gender', 'emailAddress', 'phoneNumber', 'position', 'createdOn', 'action'];
  dataSource: MatTableDataSource<Staff> = new MatTableDataSource<Staff>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'staff',
  };

  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  staffs: Array<Staff> = [];


  constructor(
    protected positionService: PositionService,
    private userService: UserService,
    private dialogService: NbDialogService,
    private router: Router,
    ) {
  }

  ngOnInit() {
    this.loadUsers();
  }
  open() {
    this.dialogService.open(StaffCreateComponent, {
      context: {
        title: 'Add New Staff',
      },
    }).onClose.subscribe(data => {
      this.loadUsers();
    });
  }

  edit(staff: Staff) {
    this.dialogService.open(StaffCreateComponent, {
      context: {
        title: 'Edit Staff',
        action: 'edit',
        staffId: staff.id.toString(),
      },
    }).onClose.subscribe(_data => {
      this.loadUsers();
    });
  }
  view(staff: Staff) {
    this.router.navigate([this.baseURL + '/' + staff.id ]);
  }

  delete(staff: Staff) {
    this.dialogService.open(UserDeleteComponent, {
      context: {
        title: 'Delete Staff',
        action: 'delete',
        userType: 'staff',
        userId: staff.id.toString(),
        name: staff.fullName,
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
      const resp = result;
      const status = resp.status;
      this.isLoading = false;
      if (status !== null && status === 200 && resp.body) {
        const data = resp.body.data;
        this.totalNumberOfItems = data.count;
        this.dataSource = new MatTableDataSource<Staff>(data.staffs);
      }
    });
  }
}
