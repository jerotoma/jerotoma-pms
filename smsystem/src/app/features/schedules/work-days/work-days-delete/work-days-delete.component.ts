import { Component, OnInit} from '@angular/core';

import { NbDialogRef } from '@nebular/theme';
import { MeetingTimeService,  ModalService } from 'app/services';
import { ShowMessage } from 'app/models';


@Component({
  selector: 'app-work-days-delete',
  templateUrl: './work-days-delete.component.html',
  styleUrls: ['./work-days-delete.component.scss']
})
export class WorkDaysDeleteComponent implements OnInit {

  meetingTimeId: string = '0';
  title: string = '';
  action: string = '';
  day: string = '';
  workDayId: string;
  confirmed: boolean = false;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  constructor(
    private meetingTimeService: MeetingTimeService,
    private modalService: ModalService,
    protected ref: NbDialogRef<WorkDaysDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteMeetingTime() {
    if (this.confirmed) {
      this.meetingTimeService.deleteMeetingTime(parseInt(this.meetingTimeId, 10))
        .subscribe((result: any ) => {
          if (result) {
            this.dismiss();
            this.modalService.openSnackBar('Meeting Time has been deleted', 'success');
          }
      });
    }
  }

  dismiss() {
    this.ref.close();
  }
  onConfirmed() {
    this.confirmed = true;
    this.deleteMeetingTime();
  }

}
