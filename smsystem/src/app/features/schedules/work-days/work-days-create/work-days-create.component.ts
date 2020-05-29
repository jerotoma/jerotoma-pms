import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import {
  WorkDay,
  WeekDay,
  Time,
  ResponseWrapper } from 'app/models';
import {
  WorkDayService,
  ModalService,
} from 'app/services';
import { QueryParam, DAYS } from 'app/utils';

@Component({
  selector: 'app-work-days-create',
  templateUrl: './work-days-create.component.html',
  styleUrls: ['./work-days-create.component.scss']
})
export class WorkDaysCreateComponent implements OnInit {

  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;

  currentDate: Date = new Date();
  workDayForm: FormGroup;
  workDay: WorkDay;
  days: WeekDay[] = DAYS;
  startTime: Time =  { hour: this.currentDate.getHours(), minute: this.currentDate.getMinutes(), second: this.currentDate.getSeconds()};
  endTime: Time = { hour: 1 + this.currentDate.getHours(), minute: this.currentDate.getMinutes(), second: this.currentDate.getSeconds()};
  seconds = true;
  listDisplay: string = 'none';
  isSubmitting: boolean = false;
  time: string = '';

  constructor(
    private workDayService:  WorkDayService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<WorkDaysCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === 'edit') {
        this.loadWorkDay();
    }
  }
  patchWorkDay() {
    this.workDayForm.patchValue({
      id: this.workDay.id,
      dayId: this.workDay.day,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.workDay = this.workDayForm.value;
        if (this.action === 'edit') {
            this.updateWorkDay();
        } else {
          this.workDayService.createWorkDay(this.workDay)
              .subscribe((workDay: WorkDay ) => {
                this.isSubmitting = false;
                  if (workDay) {
                    this.workDay = workDay;
                    this.modalService.openSnackBar('WorkDay ' + workDay.day + ' has been created', 'success');
                    this.dismiss();
                  }
              });
        }

  }
  updateWorkDay() {
    this.workDayService.updateWorkDay(this.workDay)
        .subscribe((workDay: WorkDay ) => {
          this.isSubmitting = false;
          if (workDay) {
            this.workDay = workDay;
            this.modalService.openSnackBar('WorkDay ' + workDay.day + ' has been updated', 'success');
            this.dismiss();
          }
      });
    }
  getDescriptionContent(description: string) {
   if (description) {
      this.workDayForm.patchValue({
        description: description,
      });
    }
  }

  loadForm() {
    this.workDayForm = this.formBuilder.group({
      id: [null],
      dayId: ['', Validators.required ],
    });
  }

  loadWorkDay() {
    this.workDayService.getWorkDay(parseInt(this.id, 10)).subscribe((workDay: WorkDay) => {
      if (workDay) {
        this.workDay = workDay;
        this.patchWorkDay();
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
      userType: 'workDay',
    };
  }

}
