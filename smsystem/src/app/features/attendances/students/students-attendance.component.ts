import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { ClassAttendenceCreateComponent } from './class-attendance-create/class-attendance-create.component';

import { MeetingTime, ResponseWrapper } from 'app/models';
import { MeetingTimeService } from 'app/services';
import { QueryParam, APP_ACTION_TYPE} from 'app/utils';

@Component({
  selector: 'app-students',
  templateUrl: './students-attendance.component.html',
  styleUrls: ['./students-attendance.component.scss'],
})
export class StudentsAttendanceComponent implements OnInit {

  constructor(
    private meetingTimeService: MeetingTimeService,
    private dialogService: NbDialogService,
    ) {
  }

  ngOnInit() {
  }


  open() {
    this.dialogService.open(ClassAttendenceCreateComponent, {
      context: {
        title: 'Add New Class Attendance',
        action: APP_ACTION_TYPE.create,
      }
    }).onClose.subscribe(_data => {

    });
  }

}
