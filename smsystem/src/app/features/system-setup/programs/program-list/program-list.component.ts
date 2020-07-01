import { Component, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { DeleteModalComponent } from 'app/shared';
import { Program, ResponseWrapper } from 'app/models';
import { ProgramService } from 'app/services';
import { QueryParam } from 'app/utils';


import { ProgramCreateComponent } from '../program-create/program-create.component';
import { ProgramEditComponent } from '../program-edit/program-edit.component';
import { ProgramShowComponent } from '../program-show/program-show.component';

@Component({
  selector: 'app-program-list',
  templateUrl: `./program-list.component.html`,
  styleUrls: [`./program-list.component.scss`],
})
export class ProgramListComponent implements OnInit {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Programs';
  position: Program;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'code', 'description', 'numberOfAcademicLevels', 'createdOn', 'action'];
  dataSource: MatTableDataSource<Program> = new MatTableDataSource<Program>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

   constructor(
    private programService: ProgramService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadPrograms();
  }

  open() {
    this.dialogService.open(ProgramCreateComponent, {
      context: {
        title: 'Add New Program',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadPrograms();
    });
  }

  loadPrograms() {
    this.isLoading = true;
    this.programService.getPrograms(this.param)
      .subscribe((resp: ResponseWrapper) => {
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<Program>(data.programs);
        }
      });
  }
  edit(program: Program) {
    this.dialogService.open(ProgramEditComponent, {
      context: {
        program: program,
      },
    }).onClose.subscribe(_data => {
      this.loadPrograms();
    });
  }
  delete(program: Program) {
    this.dialogService.open(DeleteModalComponent, {
      context: {
        title: 'Delete Program',
        action: 'delete',
        id: program.id + '',
        name: program.name,
      },
    }).onClose.subscribe(_data => {
      this.loadPrograms();
    });
  }

  view(program: Program) {
    this.dialogService.open(ProgramShowComponent, {
      context: {
        program: program,
      },
    }).onClose.subscribe(_data => {
      this.loadPrograms();
    });
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadPrograms();
  }
}
