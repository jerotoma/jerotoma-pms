import { Component, OnInit, Input, Output, EventEmitter, AfterContentInit, AfterViewInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import {
  TimeScope,
  Timetable,
  Event,
  TimetableRenderer,
} from 'app/timetable';
import {
  MeetingTime,
  ClassView,
  TimeSlot,
  WeekDay,
  WorkDay,
  Time,
  ResponseWrapper } from 'app/models';
import {
  MeetingTimeService,
  ClassService,
  WorkDayService,
  ModalService,
} from 'app/services';
import { QueryParam, DAYS, getWeekDay, getDateTime } from 'app/utils';

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class TimetableComponent implements OnInit, AfterViewInit, AfterContentInit {

  timeSlot: TimeSlot;
  timeSlots: TimeSlot[];
  events: Event[] = [];
  event: Event;
  isLoading: boolean;
  timetable: Timetable;
  timeTableRenderer: TimetableRenderer;
  timeScope: TimeScope;

  constructor(
    private classService: ClassService,
    private workDayService: WorkDayService,
    private  meetingTimeService:  MeetingTimeService,
  ) { }

  ngAfterViewInit(): void {
    // this.drawTimetable();
  }

  ngAfterContentInit(): void {

  }

  ngOnInit() {
   this.loadClasses(2);
  }


  drawTimetable(events: Event[]) {
    this.timetable =  new Timetable();
    this.timetable.setScope(8, 16);
    this.timetable.addWeekDays(DAYS);
    this.timetable.addEvents(events);
    this.timeTableRenderer = new TimetableRenderer(this.timetable);
    this.timeTableRenderer.draw('.timetable');
  }

  loadClasses(academicYearId: number) {
    this.isLoading = true;
    this.classService.loadJClassesByAcademicYear(academicYearId)
      .subscribe((mClasses: ClassView[]) => {
        this.isLoading = false;
        if (mClasses && mClasses.length > 0) {
          mClasses.forEach( (mClass, index) => {
            const startTime =  getDateTime(mClass.meetingTime.startTime);
            const endTime = getDateTime(mClass.meetingTime.endTime);
            const courseName = mClass.course.name + ' (' + mClass.course.code + ')';
            const weekDay = new WeekDay(mClass.meetingTime.workDay.dayId, getWeekDay(mClass.meetingTime.workDay.dayId));
            const content = '<p> Room: ' + mClass.room.name + '</p><p> Teacher: ' + mClass.teacher.fullName + '</p><p> Time: ' + mClass.meetingTime.time + '</p>';
            this.events.push(new Event(courseName, weekDay, startTime, endTime, content));
          });
          this.drawTimetable(this.events);
        }
      }, error => {
        this.isLoading = false;
      });
  }

}
