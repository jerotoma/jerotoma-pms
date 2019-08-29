import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { SchoolClassCreateComponent } from '../school-class-create/school-class-create.component';
import { SchoolClassDeleteComponent } from '../school-class-delete/school-class-delete.component';
import { AcademicDiscipline } from 'app/models/academic-disciplines';
import { AcademicDisciplineService } from 'app/services/academic-disciplines';
import { QueryParam } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-school-classes-view',
  styleUrls: ['school-classes-view.component.scss'],
  templateUrl: 'school-classes-view.component.html',
})
export class SchoolClassesViewComponent implements OnInit {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Class';
  academicDiscipline: AcademicDiscipline;
  hidePageSize: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'code', 'description', 'action'];
  dataSource: MatTableDataSource<Position> = new MatTableDataSource<Position>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private academicDisciplineService: AcademicDisciplineService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadSchoolClasses();
  }

  open() {
    this.dialogService.open(SchoolClassCreateComponent, {
      context: {
        title: 'Add New Class',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadSchoolClasses();
    });
  }

  loadSchoolClasses() {
    this.academicDisciplineService.getAcademicDisciplines(this.param)
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200 && resp.body) {
          const data = resp.body.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<Position>(data.academicDisciplines);
        }
      }, error => {

    });
  }
  edit(academicDiscipline: AcademicDiscipline) {
    this.dialogService.open(SchoolClassCreateComponent, {
      context: {
        title: 'Edit Class',
        action: 'edit',
        id: academicDiscipline.id.toString(),
        code: academicDiscipline.code,
        name: academicDiscipline.name,
        description: academicDiscipline.description,
      },
    }).onClose.subscribe(_data => {
      this.loadSchoolClasses();
    });
  }
  delete(academicDiscipline: AcademicDiscipline) {
    this.dialogService.open(SchoolClassDeleteComponent, {
      context: {
        title: 'Delete Class',
        action: 'delete',
        positionId: academicDiscipline.id.toString(),
        name: academicDiscipline.name,
      },
    }).onClose.subscribe(_data => {
      this.loadSchoolClasses();
    });
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadSchoolClasses();
  }
}
