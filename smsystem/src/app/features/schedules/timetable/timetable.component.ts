import { Component, OnInit, Input, Output, EventEmitter, AfterContentInit, AfterViewInit, ViewEncapsulation } from '@angular/core';
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
  encapsulation: ViewEncapsulation.None,
})
export class TimetableComponent implements OnInit, AfterViewInit, AfterContentInit {

  timeSlot: TimeSlot;
  timeSlots: TimeSlot[];

  timetable: Timetable;
  timeTableRenderer: TimetableRenderer;
  timeScope: TimeScope;

  constructor() { }

  ngAfterViewInit(): void {
    this.drawTimetable();
  }

  ngAfterContentInit(): void {

  }

  ngOnInit() {
    this.drawTimetable();
  }


  drawTimetable() {
    this.timetable =  new Timetable();
    this.timetable.setScope(9, 3);
    this.timetable.addLocations(['Rotterdam', 'Madrid', 'Los Angeles', 'London', 'New York', 'Jakarta', 'Tokyo']);
    this.timetable.addEvent('Sightseeing', 'Rotterdam', new Date(2015, 7, 17, 9, 0), new Date(2015, 7, 17, 11, 30), { url: '#' });
    this.timetable.addEvent('Zumba', 'Madrid', new Date(2015, 7, 17, 12), new Date(2015, 7, 17, 13), { url: '#' });
    this.timetable.addEvent('Zumbu', 'Madrid', new Date(2015, 7, 17, 13, 30), new Date(2015, 7, 17, 15), { url: '#' });
    this.timetable.addEvent('Lasergaming', 'London', new Date(2015, 7, 17, 17, 45), new Date(2015, 7, 17, 19, 30), { class: 'vip-only', data: { maxPlayers: 14, gameType: 'Capture the flag' } });
    this.timetable.addEvent('All-you-can-eat grill', 'New York', new Date(2015, 7, 17, 21), new Date(2015, 7, 18, 1, 30), { url: '#' });
    this.timetable.addEvent('Hackathon', 'Tokyo', new Date(2015, 7, 17, 11, 30), new Date(2015, 7, 17, 20)); // options attribute is not used for this event
    this.timetable.addEvent('Tokyo Hackathon Livestream', 'Los Angeles', new Date(2015, 7, 17, 12, 30), new Date(2015, 7, 17, 16, 15)); // options attribute is not used for this event
    this.timetable.addEvent('Lunch', 'Jakarta', new Date(2015, 7, 17, 9, 30), new Date(2015, 7, 17, 11, 45), { onClick: function(event) {
      window.alert('You clicked on the ' + event.name + ' event in ' + event.location + '. This is an example of a click handler');
    }});

    this.timeTableRenderer = new TimetableRenderer(this.timetable);
    this.timeTableRenderer.draw('.timetable');
  }

}
