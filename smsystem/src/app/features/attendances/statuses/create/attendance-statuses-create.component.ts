import { from } from 'rxjs';
import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import { AttendanceStatus } from 'app/models';
import { ModalService, AttendanceStatusService } from 'app/services';
import { APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-attendance-statuses-create',
  templateUrl: './attendance-statuses-create.component.html',
  styleUrls: ['./attendance-statuses-create.component.scss'],
})
export class AttendanceStatusesCreateComponent implements OnInit {

  @Input() title: string;
  @Input() action: string;
  @Input() id: string;
  @Input() attendanceStatus: AttendanceStatus;

  attendanceStatusForm: FormGroup;
  isSubmitting: boolean = false;
  attendanceStatuses: AttendanceStatus[];

  constructor(
    private attendanceStatusService: AttendanceStatusService,
    private modalService: ModalService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<AttendanceStatusesCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === APP_ACTION_TYPE.edit && this.attendanceStatus) {
      this.patchValues();
    }
  }
  patchValues() {
    this.attendanceStatusForm.patchValue({
      id: this.attendanceStatus.id,
      name: this.attendanceStatus.name,
      description: this.attendanceStatus.discription,
    });
  }

  onSubmit() {
    if (this.action === APP_ACTION_TYPE.edit && this.attendanceStatus) {
      this.attendanceStatusService.updateAttendanceStatus(this.attendanceStatusForm.value)
      .subscribe((attendanceStatus: AttendanceStatus) => {
        if (attendanceStatus) {
          this.modalService.openSnackBar('Attendance Status has been updated', 'success');
          this.dismiss();
        }
      });
    } else {
      this.attendanceStatusService.createAttendanceStatus(this.attendanceStatusForm.value)
      .subscribe((attendanceStatus: AttendanceStatus) => {
        if (attendanceStatus) {
          this.modalService.openSnackBar('Attendance Status has been created', 'success');
          this.dismiss();
        }
      });
    }
  }

  loadForm() {
    this.attendanceStatusForm = this.formBuilder.group({
      id: [null],
      name: [null, Validators.required],
      description: [null],
    });
  }

  dismiss() {
    this.ref.close();
  }
}
