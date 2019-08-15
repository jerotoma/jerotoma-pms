
import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { PositionCreateComponent } from '../position-create/position-create.component';
import { PositionDeleteComponent } from '../position-delete/position-delete.component';
import { Position } from 'app/models/positions/position.model';
import { PositionService } from 'app/services/positions';
import { QueryParam } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-positions-view',
  styleUrls: ['positions-view.component.scss'],
  templateUrl: 'positions-view.component.html',
})
export class PositionsViewComponent implements OnInit {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Positions';
  position: Position;
  hidePageSize: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'code', 'description', 'action'];
  dataSource: MatTableDataSource<Position> = new MatTableDataSource<Position>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private positionService: PositionService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadPositions();
  }

  open() {
    this.dialogService.open(PositionCreateComponent, {
      context: {
        title: 'Add New Position',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadPositions();
    });
  }

  loadPositions() {
    this.positionService.getPositions(this.param)
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200 && resp.body) {
          const data = resp.body.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<Position>(data.positions);
        }
      }, error => {

    });
  }
  edit(position: Position) {
    this.dialogService.open(PositionCreateComponent, {
      context: {
        title: 'Edit Position',
        action: 'edit',
        id: position.id.toString(),
        code: position.code,
        name: position.name,
        description: position.description,
      },
    }).onClose.subscribe(_data => {
      this.loadPositions();
    });
  }
  delete(position: Position) {
    this.dialogService.open(PositionDeleteComponent, {
      context: {
        title: 'Delete Position',
        action: 'delete',
        positionId: position.id.toString(),
        name: position.name,
      },
    }).onClose.subscribe(_data => {
      this.loadPositions();
    });
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadPositions();
  }
}
