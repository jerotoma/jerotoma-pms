import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NbDialogRef } from '@nebular/theme';
import { PositionService } from 'app/services';
import { ShowMessage } from 'app/models/messages/show-message.model';
@Component({
  selector: 'app-position-delete',
  templateUrl: 'position-delete.component.html',
  styleUrls: ['position-delete.component.scss'],
})
export class PositionDeleteComponent implements OnInit {
  positionId: string = '0';
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
    private positionService:  PositionService,
    protected ref: NbDialogRef<PositionDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deletePosition() {
    if (this.confirmed) {
      this.positionService.deletePosition(parseInt(this.positionId, 10))
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
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
    this.deletePosition();
  }

}
