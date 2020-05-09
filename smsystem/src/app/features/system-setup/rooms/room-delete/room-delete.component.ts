import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { RoomService } from 'app/services';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-class-room-delete',
  templateUrl: 'room-delete.component.html',
  styleUrls: ['room-delete.component.scss'],
})
export class RoomDeleteComponent implements OnInit {
  roomId: string = '0';
  title: string = '';
  action: string = '';
  name: string = '';
  confirmed: boolean = false;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  constructor(
    private roomService: RoomService,
    protected ref: NbDialogRef<RoomDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteSchoolClass() {
    if (this.confirmed) {
    this.roomService.deleteRoom(parseInt(this.roomId, 10)).subscribe((result: boolean ) => {
        if (result) {
          this.dismiss();
        }
      });
    }
  }

  dismiss() {
    this.ref.close();
  }
  onConfirmed() {
    this.confirmed = true;
    this.deleteSchoolClass();
  }

}
