import { Component, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { WorkDaysCreateComponent } from './work-days-create/work-days-create.component';
import { WorkDaysDeleteComponent } from './work-days-delete/work-days-delete.component';
import { WorkDay, ResponseWrapper } from 'app/models';
import { WorkDayService } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-work-days',
  templateUrl: './work-days.component.html',
  styleUrls: ['./work-days.component.scss'],
})
export class WorkDaysComponent implements OnInit {
  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Work Days';
  workDay: WorkDay;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'day', 'createdOn', 'action'];
  dataSource: MatTableDataSource<WorkDay> = new MatTableDataSource<WorkDay>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private workDayService: WorkDayService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadWorkDays();
  }

  open() {
    this.dialogService.open(WorkDaysCreateComponent, {
      context: {
        title: 'Add New Work Day',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadWorkDays();
    });
  }

  loadWorkDays() {
    this.isLoading = true;
    this.workDayService.getWorkDaysPaginated(this.param)
      .subscribe((resp: ResponseWrapper) => {
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<WorkDay>(data.workDays);
        }
      });
  }
  edit(workDay: WorkDay) {
    this.dialogService.open(WorkDaysCreateComponent, {
      context: {
        title: 'Edit Meeting Time',
        action: 'edit',
        id: workDay.id.toString(),
      },
    }).onClose.subscribe(_data => {
      this.loadWorkDays();
    });
  }
  delete(workDay: WorkDay) {
    this.dialogService.open(WorkDaysDeleteComponent, {
      context: {
        title: 'Delete Work Day',
        action: 'delete',
        workDayId: workDay.id.toString(),
        day: workDay.day.toString(),
      },
    }).onClose.subscribe(_data => {
      this.loadWorkDays();
    });
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadWorkDays();
  }
}
