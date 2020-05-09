import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { RoomCreateComponent } from '../room-create/room-create.component';
import { RoomDeleteComponent } from '../room-delete/room-delete.component';
import { Room, ResponseWrapper} from 'app/models';
import { RoomService } from 'app/services';
import { QueryParam } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-class-rooms-view',
  styleUrls: ['rooms-view.component.scss'],
  templateUrl: 'rooms-view.component.html',
})
export class RoomsViewComponent implements OnInit {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Class Rooms';
  classRoom: Room;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'code', 'capacity', 'description', 'action'];
  dataSource: MatTableDataSource<Room> = new MatTableDataSource<Room>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private roomService: RoomService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadSchoolClasses();
  }

  open() {
    this.dialogService.open(RoomCreateComponent, {
      context: {
        title: 'Add New Class',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadSchoolClasses();
    });
  }

  loadSchoolClasses() {
    this.isLoading = true;
    this.roomService.getRooms(this.param)
    .subscribe((resp: ResponseWrapper) => {
      this.isLoading = false;
      if (resp) {
        const data = resp.data;
        this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<Room>(data.rooms);
      }
    });
  }
  edit(room: Room) {
    this.dialogService.open(RoomCreateComponent, {
      context: {
        title: 'Edit Class',
        action: 'edit',
        id: room.id.toString(),
        code: room.code,
        name: room.name,
        capacity: room.capacity.toString(),
        description: room.description,
      },
    }).onClose.subscribe(_data => {
      this.loadSchoolClasses();
    });
  }
  delete(room: Room) {
    this.dialogService.open(RoomDeleteComponent, {
      context: {
        title: 'Delete Class',
        action: 'delete',
        roomId: room.id.toString(),
        name: room.name,
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
