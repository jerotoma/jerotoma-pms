import { Component, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { StreamCreateComponent, StreamDeleteComponent } from 'app/shared';
import { Stream, ResponseWrapper} from 'app/models';
import { StreamService } from 'app/services';
import { QueryParam, APP_ACTION_TYPE } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-class-streams-view',
  styleUrls: ['stream-view.component.scss'],
  templateUrl: 'stream-view.component.html',
})
export class StreamViewComponent implements OnInit {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Class Streams';
  stream: Stream;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'code', 'capacity', 'description', 'action'];
  dataSource: MatTableDataSource<Stream> = new MatTableDataSource<Stream>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private streamService: StreamService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadSchoolClasses();
  }

  open() {
    this.dialogService.open(StreamCreateComponent, {
      context: {
        title: 'Add New Class',
        action: APP_ACTION_TYPE.create,
      },
    }).onClose.subscribe(_data => {
      this.loadSchoolClasses();
    });
  }

  loadSchoolClasses() {
    this.isLoading = true;
    this.streamService.getStreamPaginated(this.param)
    .subscribe((resp: ResponseWrapper) => {
      this.isLoading = false;
      if (resp) {
        const data = resp.data;
        this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<Stream>(data.streams);
      }
    });
  }
  edit(stream: Stream) {
    this.dialogService.open(StreamCreateComponent, {
      context: {
        title: 'Edit Stream',
        action: APP_ACTION_TYPE.edit,
        stream: stream
      },
    }).onClose.subscribe(_data => {
      this.loadSchoolClasses();
    });
  }
  delete(stream: Stream) {
    this.dialogService.open(StreamDeleteComponent, {
      context: {
        title: 'Delete Stream',
        action: APP_ACTION_TYPE.delete,
        streamId: stream.id,
        name: stream.name,
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
