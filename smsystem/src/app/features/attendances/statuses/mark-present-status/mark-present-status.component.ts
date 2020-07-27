import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import {AttendanceStatus, SystemConfig } from 'app/models';
import { AttendanceStatusService, SystemConfigService } from 'app/services';
import { QueryParam, APP_CONSTANTS } from 'app/utils';

@Component({
  selector: 'app-mark-present-status',
  templateUrl: './mark-present-status.component.html',
  styleUrls: ['./mark-present-status.component.scss'],
})
export class MarkPresentStatusComponent implements OnInit {

  @Input() attendanceStatus: AttendanceStatus;
  @Input('allowUpdate') allowUpdate: boolean = false;
  @Input('statusLabel') statusLabel: string = 'Select status to mark it as the present status';
  @Input('title') title: string = 'Attendance Status marked as Present';
  @Output() onChange: EventEmitter<AttendanceStatus> = new EventEmitter();
  attendancePresentStatusForm: FormGroup;
  attendancePresentStatus: string = APP_CONSTANTS.attendancePresentStatus;
  attendanceStatuses: AttendanceStatus[];


  constructor(
    private attendanceStatusService: AttendanceStatusService,
    private formBuilder: FormBuilder,
    private systemConfigService: SystemConfigService) {}

  ngOnInit() {
      this.loadForm();
      this.loadAttendanceStatuses();
  }

  loadForm() {
    this.attendancePresentStatusForm = this.formBuilder.group({
      attendanceStatusId: [null, Validators.required],
    });
    this.onFormChanges();
  }

  onFormChanges(): void {
    this.attendancePresentStatusForm.get('attendanceStatusId').valueChanges.subscribe((attendanceStatusId: number) => {
      window.console.log(' Status ID:' + attendanceStatusId, ' Allow Update:' + this.allowUpdate);
      if (attendanceStatusId) {
        const systemConfig: SystemConfig = {
            id: null,
            name: this.attendancePresentStatus,
            value: '' + attendanceStatusId,
        };
        if (this.allowUpdate && systemConfig) {
          this.updateSystemConfigChange(systemConfig);
        }
        this.setAttendanceStatus(attendanceStatusId);
      }
    });
  }

  updateSystemConfigChange(systemConfig: SystemConfig) {
    this.systemConfigService.updateSystemConfig(systemConfig)
    .subscribe((data: SystemConfig ) => {
      if (data) {
        this.attendancePresentStatusForm.patchValue({
          attendanceStatusId: parseInt(data.value, 10),
        }, {emitEvent: false});
        this.setAttendanceStatus(parseInt(data.value, 10));
      }
    }, error => {
    });
  }

  loadAttendanceStatuses() {
    this.attendanceStatusService.loadAttendanceStatuses().subscribe((attendanceStatuses: AttendanceStatus[] ) => {
        if (attendanceStatuses) {
          this.attendanceStatuses = attendanceStatuses;
          this.loadSystemConfigChange(this.attendancePresentStatus);
        }
      });
  }

  loadSystemConfigChange(uniqueKey: string) {
    this.systemConfigService.findSystemConfigByKey(uniqueKey)
    .subscribe((data: SystemConfig ) => {
      if (data) {
        this.attendancePresentStatusForm.patchValue({
          attendanceStatusId: parseInt(data.value, 10),
        }, {emitEvent: false});
        this.setAttendanceStatus(parseInt(data.value, 10));
      }
    }, error => {

    });
  }

  setAttendanceStatus(attendanceStatusId: number) {
    this.attendanceStatus = this.attendanceStatuses.find((attendanceStatus) => attendanceStatus.id === attendanceStatusId);
    this.onChange.emit(this.attendanceStatus);
  }
}
