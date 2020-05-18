
import {Component, Input, Output, EventEmitter, OnInit} from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Time } from 'app/models';

@Component({
  selector: 'app-timepicker',
  template: `<form  autocomplete="off" [formGroup]="timePickerForm">
      <ngb-timepicker
        formControlName="time"
        [meridian]="meridian"
        [spinners]="spinners"
        [seconds]="second">
      </ngb-timepicker>
  </form>`,
})
export class TimepickerComponent implements OnInit {
    currentDate: Date = new Date();
    @Input('time') time: Time =  { hour: this.currentDate.getHours(), minute: this.currentDate.getMinutes(), second: this.currentDate.getSeconds()};
    @Input('second') second: boolean = false;
    @Input('meridian') meridian: boolean = true;
    @Input('spinners') spinners: boolean = true;

    @Output() onChange: EventEmitter<Time> = new EventEmitter();

    timePickerForm: FormGroup;
    constructor(private formBuilder: FormBuilder){}

    ngOnInit(): void {
      this.loadForm();
    }

   loadForm() {
    this.timePickerForm = this.formBuilder.group({
      time: [this.time, Validators.required]
    });
    this.onTimeChanges();
  }

  onTimeChanges() {
    this.timePickerForm.get('time').valueChanges.subscribe((time: Time) => {
      this.time = time;
      this.onChange.emit(this.time);
    });
  }
}
