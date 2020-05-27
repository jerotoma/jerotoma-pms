import { Component, OnInit, Input, Output, EventEmitter, AfterContentInit, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import {
  TimeScope,
  Timetable,
  TimetableRenderer,
} from 'app/timetable';
import {
  MeetingTime,
  TimeSlot,
  Day,
  WorkDay,
  Time,
  ResponseWrapper } from 'app/models';
import {
  MeetingTimeService,
  WorkDayService,
  ModalService,
} from 'app/services';
import { QueryParam, DAYS } from 'app/utils';

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.scss'],
})
export class TimetableComponent implements OnInit, AfterViewInit, AfterContentInit {

  timeSlot: TimeSlot;
  timeSlots: TimeSlot[];

  timeTable: Timetable;
  timetableRenderer: TimetableRenderer;
  timeScope: TimeScope;

  constructor() { }

  ngAfterViewInit(): void {
    this.drawTimetable();
  }

  ngAfterContentInit(): void {

  }

  ngOnInit() {

  }


  drawTimetable() {
    this.timeTable =  new Timetable();
    this.timetableRenderer = new TimetableRenderer(this.timeTable);
    this.timetableRenderer.draw('.timetable');
  }

}
