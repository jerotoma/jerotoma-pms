import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { SchoolClassService } from 'app/services';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-class-room-delete',
  templateUrl: 'class-room-delete.component.html',
  styleUrls: ['class-room-delete.component.scss'],
})
export class ClassRoomDeleteComponent implements OnInit {
  schoolClassId: string = '0';
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
    private schoolClassService: SchoolClassService,
    protected ref: NbDialogRef<ClassRoomDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteSchoolClass() {
    if (this.confirmed) {
    this.schoolClassService.deleteSchoolClass(parseInt(this.schoolClassId, 10)).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const data = resp.body;
        const status = resp.status;
        if (status !== null && status === 200) {
          this.dismiss();
        } else {
          this.showMessage.success = false;
          this.showMessage.error = true;
          this.showMessage.message = data  ? data.message : '';
        }
      }, error => {
        this.showMessage.error = true;
        this.showMessage.success = false;
        this.showMessage.message = error ? error.error.message : '';
      }
);
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
