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
  ClassView,
  TimeSlot,
  WeekDay,
  AcademicYear,
  SystemConfig,
  } from 'app/models';
import {
  SystemConfigService,
  ClassService,
} from 'app/services';
import { APP_CONSTANTS, DAYS, getWeekDay, getDateTime } from 'app/utils';

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
  academicYear: AcademicYear;
  currentAcademicYear: string = APP_CONSTANTS.currentAcademicYear;

  constructor(
    private systemConfigService: SystemConfigService,
    private classService: ClassService,
  ) { }

  ngAfterViewInit(): void {
    // this.drawTimetable();
  }

  ngAfterContentInit(): void {

  }

  ngOnInit() {
    this.loadTimeTableByCurrentAcademicYear(this.currentAcademicYear);
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
            const content = '<p> Room: ' + mClass.room.name + ' (' + mClass.room.code + ')</p><p> Teacher: ' + mClass.teacher.fullName + '</p><p> Time: ' + mClass.meetingTime.time + '</p>';
            this.events.push(new Event(courseName, weekDay, startTime, endTime, content));
          });
          this.drawTimetable(this.events);
        }
      }, error => {
        this.isLoading = false;
      });
  }

  onCurrentAcademicYearChange(academicYear: AcademicYear) {
    this.academicYear = academicYear;
    this.loadClasses(academicYear.id);
  }

  loadTimeTableByCurrentAcademicYear(uniqueKey: string) {
    this.systemConfigService.findSystemConfigByKey(uniqueKey)
    .subscribe((data: SystemConfig ) => {
      if (data) {
        this.loadClasses(parseInt(data.value, 10));
      }
    }, error => {

    });
  }

}
