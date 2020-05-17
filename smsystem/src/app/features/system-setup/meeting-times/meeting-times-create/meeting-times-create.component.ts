import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { MeetingTime, ResponseWrapper } from 'app/models';
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


  meetingTimeForm: FormGroup;
  meetingTime: MeetingTime;
  listDisplay: string = 'none';
  isSubmitting: boolean = false;

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
