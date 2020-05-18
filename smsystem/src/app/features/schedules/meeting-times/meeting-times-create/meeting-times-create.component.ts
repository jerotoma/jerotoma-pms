import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import {
  MeetingTime,
  Time,
  ResponseWrapper } from 'app/models';
import {
  MeetingTimeService,
  ModalService,
} from 'app/services';
import { QueryParam } from 'app/utils';
@Component({
  selector: 'app-meeting-times-create',
  templateUrl: './meeting-times-create.component.html',
  styleUrls: ['./meeting-times-create.component.scss'],
})
export class MeetingTimesCreateComponent implements OnInit {

  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;

  currentDate: Date = new Date();
  meetingTimeForm: FormGroup;
  meetingTime: MeetingTime;
  startTime: Time =  { hour: this.currentDate.getHours(), minute: this.currentDate.getMinutes(), second: this.currentDate.getSeconds()};
  endTime: Time = { hour: 1 + this.currentDate.getHours(), minute: this.currentDate.getMinutes(), second: this.currentDate.getSeconds()};
  seconds = true;
  listDisplay: string = 'none';
  isSubmitting: boolean = false;
  time: string = '';

  constructor(
    private meetingTimeService:  MeetingTimeService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<MeetingTimesCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === 'edit') {
        this.loadMeetingTime();
    }
  }
  patchMeetingTime() {
    this.meetingTimeForm.patchValue({
      time: this.meetingTime.time,
      id: this.meetingTime.id,
      endTime: this.meetingTime.endTime,
      startTime: this.meetingTime.startTime,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.meetingTime = this.meetingTimeForm.value;
        if (this.action === 'edit') {
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
              });
        }

  }
  updateMeetingTime() {
    this.meetingTimeService.updateMeetingTime(this.meetingTime)
        .subscribe((meetingTime: MeetingTime ) => {
          this.isSubmitting = false;
          if (meetingTime) {
            this.meetingTime = meetingTime;
            this.modalService.openSnackBar('MeetingTime ' + meetingTime.time + ' has been updated', 'success');
            this.dismiss();
          }
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
      startTime: [null, Validators.required ],
      endTime: [null, Validators.required ],
    });
    this.updateTime();
    this.onEndTimeChange(this.endTime);
    this.onStartTimeChange(this.startTime);
  }
  onStartTimeChange(startTime: Time) {
    this.startTime = startTime;
    this.meetingTimeForm.patchValue({
      startTime: startTime,
    });
    this.updateTime();
  }
  onEndTimeChange(endTime: Time) {
    this.endTime = endTime;
    this.meetingTimeForm.patchValue({
      endTime: endTime,
    });
    this.updateTime();
  }

  updateTime() {
    this.time = this.startTime.hour + ':' + this.startTime.minute + ' - ' + this.endTime.hour + ':' + this.endTime.minute;
    this.meetingTimeForm.patchValue({
      time: this.time,
    });
  }

  loadMeetingTime() {
    this.meetingTimeService.getMeetingTime(parseInt(this.id, 10)).subscribe((meetingTime: MeetingTime) => {
      if (meetingTime) {
        this.meetingTime = meetingTime;
        this.patchMeetingTime();
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
}
