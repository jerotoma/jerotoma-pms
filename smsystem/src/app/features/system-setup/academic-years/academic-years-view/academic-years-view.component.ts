
import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { AcademicYearCreateComponent } from '../academic-year-create/academic-year-create.component';
import { AcademicYearDeleteComponent } from '../academic-year-delete/academic-year-delete.component';
import { AcademicDiscipline } from 'app/models/academic-disciplines';
import { AcademicDisciplineService } from 'app/services/academic-disciplines';
import { QueryParam } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-academic-years-view',
  styleUrls: ['academic-years-view.component.scss'],
  templateUrl: 'academic-years-view.component.html',
})
export class AcademicYearsViewComponent implements OnInit {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of School Academic Year';
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
    this.loadAcademicYears();
  }

  open() {
    this.dialogService.open(AcademicYearCreateComponent, {
      context: {
        title: 'Add New School Academic Year',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadAcademicYears();
    });
  }

  loadAcademicYears() {
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
    this.dialogService.open(AcademicYearCreateComponent, {
      context: {
        title: 'Edit School Academic Year',
        action: 'edit',
        id: academicDiscipline.id.toString(),
        code: academicDiscipline.code,
        name: academicDiscipline.name,
        description: academicDiscipline.description,
      },
    }).onClose.subscribe(_data => {
      this.loadAcademicYears();
    });
  }
  delete(academicDiscipline: AcademicDiscipline) {
    this.dialogService.open(AcademicYearDeleteComponent, {
      context: {
        title: 'Delete School Academic Year',
        action: 'delete',
        positionId: academicDiscipline.id.toString(),
        name: academicDiscipline.name,
      },
    }).onClose.subscribe(_data => {
      this.loadAcademicYears();
    });
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadAcademicYears();
  }
}
