import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { NbDialogService, NbMenuService } from '@nebular/theme';

import { PageEvent } from '@angular/material/paginator';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { StudentCreateComponent } from '../create/student-create.component';
import { QueryParam, APP_ACTION_TYPE, API_END_POINTS } from 'app/utils';
import { UserService } from 'app/services/users';
import { UserDeleteComponent, UploadsComponent } from 'app/shared';
import { Student } from 'app/models/users';

@Component({
  selector: 'app-student-views',
  templateUrl: 'students-view.component.html',
  styleUrls: ['students-view.component.scss'],
})
export class StudentsViewComponent implements OnInit {
  title: string = 'Student\'s List';
  baseURL: string = '/dashboard/users/students/';
  firstForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;

  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'emailAddress', 'phoneNumber', 'program', 'currentAcademicLevel',  'createdOn', 'action'];
  dataSource: MatTableDataSource<Student> = new MatTableDataSource<Student>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  items: any = [
    {
      title: 'Import Excel',
    },
    {
      title: 'Upload Excel',
    },
  ];

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
    private menuService: NbMenuService,
    private router: Router,
    ) {
  }

  ngOnInit() {
    this.loadUsers();
    this.menuService.onItemClick().subscribe(( event ) => {
      this.onItemSelection(event.item.title);
    });
  }

  onItemSelection(title: string) {
    if ( title === 'Import Excel' ) {
      this.dialogService.open(UploadsComponent, {
        context: {
          title: 'Import files',
          action:  APP_ACTION_TYPE.create,
          uploadURL: API_END_POINTS.users + '/excel/import'
        },
      }).onClose.subscribe(data => {
        this.loadUsers();
      });
    } else if ( title === 'Upload Excel' ) {
      this.dialogService.open(UploadsComponent, {
        context: {
          title: 'Add New Student',
          action: APP_ACTION_TYPE.create,
        },
      }).onClose.subscribe(data => {
        this.loadUsers();
      });
    }
  }

  open() {
    this.dialogService.open(StudentCreateComponent, {
      context: {
        title: 'Add New Student',
        action: APP_ACTION_TYPE.create,
      },
    }).onClose.subscribe(data => {
      this.loadUsers();
    });
  }

  edit(student: Student) {
    this.dialogService.open(StudentCreateComponent, {
      context: {
        title: 'Edit ' + student.fullName + '\'s Details',
        action: APP_ACTION_TYPE.edit,
        userId: student.userId,
      },
    }).onClose.subscribe(data => {
      this.loadUsers();
    });
  }

  view(student: Student) {
    this.router.navigate([this.baseURL + '/' + student.userId ]);
  }
  viewParent(student: Student) {
    this.router.navigate([this.baseURL + '/' + student.userId ]);
  }

  delete(student: Student) {
    this.dialogService.open(UserDeleteComponent, {
      context: {
        title: 'Delete Student',
        action: APP_ACTION_TYPE.delete,
        userType: 'student',
        userId: student.userId,
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
    this.isLoading = true;
    this.userService.loadUsers(this.param).subscribe((result: any) => {
          this.isLoading = false;
        if (result) {
          this.totalNumberOfItems = result.count;
          this.dataSource = new MatTableDataSource<Student>(result.students);
        }
    });
  }
}
