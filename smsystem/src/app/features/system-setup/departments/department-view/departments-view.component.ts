import { Component, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { DepartmentCreateComponent } from '../department-create/department-create.component';
import { DepartmentDeleteComponent } from '../department-delete/departments-delete.component';
import { Department, ResponseWrapper } from 'app/models';
import { DepartmentService } from 'app/services';
import { QueryParam } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-departments-view',
  styleUrls: ['departments-view.component.scss'],
  templateUrl: 'departments-view.component.html',
})
export class DepartmentsViewComponent implements OnInit {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Departments';
  department: Department;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'createdOn', 'action'];
  dataSource: MatTableDataSource<Department> = new MatTableDataSource<Department>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private departmentService: DepartmentService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadDepartments();
  }

  open() {
    this.dialogService.open(DepartmentCreateComponent, {
      context: {
        title: 'Add New Department',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadDepartments();
    });
  }

  loadDepartments() {
    this.isLoading = true;
    this.departmentService.getDepartments(this.param)
      .subscribe((resp: ResponseWrapper) => {
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<Department>(data.departments);
        }
      });
  }
  edit(department: Department) {
    this.dialogService.open(DepartmentCreateComponent, {
      context: {
        title: 'Edit Department',
        action: 'edit',
        id: department.id.toString(),
      },
    }).onClose.subscribe(_data => {
      this.loadDepartments();
    });
  }
  delete(department: Department) {
    this.dialogService.open(DepartmentDeleteComponent, {
      context: {
        title: 'Delete Department',
        action: 'delete',
        departmentId: department.id.toString(),
        name: department.name,
      },
    }).onClose.subscribe(_data => {
      this.loadDepartments();
    });
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadDepartments();
  }
}
