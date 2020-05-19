import { Component, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { MeetingTimesCreateComponent } from './meeting-times-create/meeting-times-create.component';
import { MeetingTimesDeleteComponent } from './meeting-times-delete/meeting-times-delete.component';
import { MeetingTime, ResponseWrapper } from 'app/models';
import { MeetingTimeService } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-meeting-times-view',
  templateUrl: './meeting-times-view.component.html',
  styleUrls: ['./meeting-times-view.component.scss']
})
export class MeetingTimesViewComponent implements OnInit {
  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Meeting Times';
  meetingTime: MeetingTime;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'workDay', 'startTime', 'endTime', 'time', 'createdOn', 'action'];
  dataSource: MatTableDataSource<MeetingTime> = new MatTableDataSource<MeetingTime>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private meetingTimeService: MeetingTimeService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadMeetingTimes();
  }

  open() {
    this.dialogService.open(MeetingTimesCreateComponent, {
      context: {
        title: 'Add New Meeting Time',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadMeetingTimes();
    });
  }

  loadMeetingTimes() {
    this.isLoading = true;
    this.meetingTimeService.getMeetingTimesPaginated(this.param)
      .subscribe((resp: ResponseWrapper) => {
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<MeetingTime>(data.meetingTimes);
        }
      });
  }
  edit(meetingTime: MeetingTime) {
    this.dialogService.open(MeetingTimesCreateComponent, {
      context: {
        title: 'Edit Meeting Time',
        action: 'edit',
        id: meetingTime.id.toString(),
      },
    }).onClose.subscribe(_data => {
      this.loadMeetingTimes();
    });
  }
  delete(meetingTime: MeetingTime) {
    this.dialogService.open(MeetingTimesDeleteComponent, {
      context: {
        title: 'Delete Meeting Time',
        action: 'delete',
        meetingTimeId: meetingTime.id.toString(),
        time:  meetingTime.workDay.day + ' : ' + meetingTime.time,
      },
    }).onClose.subscribe(_data => {
      this.loadMeetingTimes();
    });
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadMeetingTimes();
  }

}
