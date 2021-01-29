import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { StreamService } from 'app/services';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-stream-delete',
  templateUrl: 'stream-delete.component.html',
  styleUrls: ['stream-delete.component.scss'],
})
export class StreamDeleteComponent implements OnInit {
  streamId: number = 0;
  title: string = '';
  action: string = '';
  name: string = '';
  confirmed: boolean = false;
  isAcademicLevel: boolean = false;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  constructor(
    private streamService: StreamService,
    protected ref: NbDialogRef<StreamDeleteComponent>) {}
  ngOnInit() {
  }

  deleteStream() {
    if (this.confirmed && !this.isAcademicLevel) {
    this.streamService.deleteStream(this.streamId).subscribe((result: boolean ) => {
        if (result) {
          this.dismiss();
        }
      });
    } else {
      this.dismiss();
    }
  }

  dismiss() {
     this.ref.close({
      confirmed: this.confirmed,
      streamId: this.streamId
    });
  }
  onConfirmed() {
    this.confirmed = true;
    this.deleteStream();
  }

}
