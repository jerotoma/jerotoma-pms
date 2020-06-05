import { from } from 'rxjs';
import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { TimepickerComponent } from 'app/shared';
import {
  MeetingTime,
  WeekDay,
  WorkDay,
  Time,
  ResponseWrapper } from 'app/models';
import {
  MeetingTimeService,
  WorkDayService,
  ModalService,
} from 'app/services';
import { QueryParam, DAYS, APP_ACTION_TYPE } from 'app/utils';
@Component({
  selector: 'app-meeting-times-create',
  templateUrl: './meeting-times-create.component.html',
  styleUrls: ['./meeting-times-create.component.scss'],
})
export class MeetingTimesCreateComponent implements OnInit {

  @Input() title: string;
  @Input() action: string = APP_ACTION_TYPE.create;
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;
  // @ViewChild(TimepickerComponent, {static: false}) appTimepicker: TimepickerComponent;

  currentDate: Date = new Date();
  meetingTimeForm: FormGroup;
  meetingTime: MeetingTime;
  startTime: Time;
  endTime: Time;
  seconds = true;
  listDisplay: string = 'none';
  prettyTime: string;
  isSubmitting: boolean = false;
  time: string = '';
  days: WeekDay[] = DAYS;
  workDay: WorkDay;
  workDays: WorkDay[];

  second: boolean = false;
  meridian: boolean = true;
  spinners: boolean = true;

  constructor(
    private workDayService:  WorkDayService,
    private meetingTimeService:  MeetingTimeService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<MeetingTimesCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadWorkDays();
    if (this.action === APP_ACTION_TYPE.edit) {
        this.loadMeetingTime();
    }
  }
  patchMeetingTime() {
    const formInput = {
      time: this.meetingTime.time,
      id: this.meetingTime.id,
      workDayId: this.meetingTime.workDay.id,
      endTime: this.meetingTime.endTime,
      startTime: this.meetingTime.startTime,
    };
    this.meetingTimeForm.patchValue(formInput);
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.meetingTime = this.meetingTimeForm.value;
        if (this.action === APP_ACTION_TYPE.edit) {
            this.updateMeetingTime();
        } else {
          this.meetingTimeService.createMeetingTime(this.meetingTime)
              .subscribe((meetingTime: MeetingTime ) => {
                this.isSubmitting = false;
                  if (meetingTime) {
                    this.meetingTime = meetingTime;
                    this.modalService.openSnackBar('MeetingTime ' + meetingTime.time + ' has been created', 'success');
                    this.dismiss();
                  }
              }, error => {
                this.isSubmitting = false;
                // console.log(error);
              });
        }

  }
  updateMeetingTime() {
    const postMeetingTime = {
      time: this.meetingTime.time,
      id: this.meetingTime.id,
      workDayId: this.meetingTime.workDayId,
      endTime: this.meetingTime.endTime,
      startTime: this.meetingTime.startTime,
    };
    this.meetingTimeService.updateMeetingTime(postMeetingTime)
        .subscribe((meetingTime: MeetingTime ) => {
          this.isSubmitting = false;
          if (meetingTime) {
            this.meetingTime = meetingTime;
            this.modalService.openSnackBar('MeetingTime ' + meetingTime.time + ' has been updated', 'success');
            this.dismiss();
          }
      }, error => {
        this.isSubmitting = false;
        // console.log(error);
      });
    }
  getDescriptionContent(description: string) {
   if (description) {
      this.meetingTimeForm.patchValue({
        description: description,
      });
    }
  }

  loadForm() {
    this.meetingTimeForm = this.formBuilder.group({
      id: [null],
      time: ['', Validators.required ],
      workDayId: [null, Validators.required ],
      startTime: [null, Validators.required ],
      endTime: [null, Validators.required ],
    });
    this.onChanges();
  }
  onChanges() {
    this.meetingTimeForm.get('startTime').valueChanges.subscribe((startTime: Time) => {
      if (startTime) {
        this.startTime = startTime;
        this.updateTime();
      }
    });
    this.meetingTimeForm.get('endTime').valueChanges.subscribe((endTime: Time) => {
      if (endTime) {
        this.endTime = endTime;
        this.updateTime();
      }
    });
  }

  updateTime() {
    if (this.startTime && this.endTime) {
      this.time = this.prettyFormatTime(this.startTime, false) + ' - ' + this.prettyFormatTime(this.endTime, false);
      this.meetingTimeForm.patchValue({
        time: this.time,
      });
    }
  }

  loadMeetingTime() {
    this.meetingTimeService.getMeetingTime(parseInt(this.id, 10)).subscribe((meetingTime: MeetingTime) => {
      if (meetingTime) {
        this.meetingTime = meetingTime;
        this.startTime = meetingTime.startTime;
        this.endTime = meetingTime.endTime;
        this.workDay = meetingTime.workDay;
        this.time = meetingTime.time;
        this.patchMeetingTime();
      }
    });
  }

  loadWorkDay(workDayId: number) {
    this.workDayService.getWorkDay(workDayId).subscribe((workDay: WorkDay) => {
      if (workDay) {
        this.workDay = workDay;
        this.meetingTimeForm.patchValue({
          workDayId: workDay.id,
        });
      }
    });
  }

  loadWorkDays() {
    this.workDayService.loadWorkDays()
      .subscribe((workDays: WorkDay[]) => {
        if (workDays) {
          this.workDays = workDays;
        }
      });
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'meetingTime',
    };
  }

  prettyFormatTime(time: Time, usingTwelveHour: boolean) {
    const minute = time.minute <= 9 ? '0' + time.minute : '' + time.minute;
    if (usingTwelveHour) {
            const period = time.hour >= 12 ? 'PM' : 'AM';
            this.prettyTime = ((time.hour + 11) % 12 + 1) + ':' + minute + period;
    } else {
      const hourPrefix = time.hour < 10 ? '0' : '';
      this.prettyTime = hourPrefix + time.hour + ':' + minute;
    }
    return this.prettyTime;
  }
}
