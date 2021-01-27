import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { DeleteModalComponent } from 'app/shared';
import { AcademicLevel, ResponseWrapper } from 'app/models';
import { AcademicLevelService } from 'app/services';
import { QueryParam } from 'app/utils';


import { AcademicLevelCreateComponent } from '../academic-level-create/academic-level-create.component';

@Component({
  selector: 'app-academic-level-list',
  templateUrl: `./academic-level-list.component.html`,
  styleUrls: [`./academic-level-list.component.scss`],
})
export class AcademicLevelListComponent implements OnInit {
  baseURL: string = '/dashboard/system-setup/academic-levels';

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Academic Levels';
  position: AcademicLevel;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'code', 'description', 'action'];
  dataSource: MatTableDataSource<AcademicLevel> = new MatTableDataSource<AcademicLevel>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

   constructor(
    private academicLevelService: AcademicLevelService,
    private dialogService: NbDialogService,
    private router: Router,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadAcademicLevels();
  }

  open() {
    this.dialogService.open(AcademicLevelCreateComponent, {
      context: {
        title: 'Add New AcademicLevel',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadAcademicLevels();
    });
  }

  loadAcademicLevels() {
    this.isLoading = true;
    this.academicLevelService.getAcademicLevels(this.param)
      .subscribe((resp: ResponseWrapper) => {
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<AcademicLevel>(data.academicLevels);
        }
      });
  }

  edit(academicLevel: AcademicLevel) {
    this.dialogService.open(AcademicLevelCreateComponent, {
      context: {
        title: 'Edit AcademicLevel',
        action: 'edit',
        id: academicLevel.id.toString(),
      },
    }).onClose.subscribe(_data => {
      this.loadAcademicLevels();
    });
  }

  delete(academicLevel: AcademicLevel) {
    this.dialogService.open(DeleteModalComponent, {
      context: {
        title: 'Delete AcademicLevel',
        action: 'delete',
        id: academicLevel.id + '',
        name: academicLevel.name,
      },
    }).onClose.subscribe(_data => {
      this.loadAcademicLevels();
    });
  }

  view(academicLevel: AcademicLevel) {
    this.router.navigate([this.baseURL + '/' + academicLevel.id ]);
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadAcademicLevels();
  }
}
