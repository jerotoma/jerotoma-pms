import { Component, OnInit} from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { ShowMessage } from 'app/models';
@Component({
  selector: 'app-delete-modal',
  template: `<nb-card>
                <nb-card-body>
                    <nb-alert outline="danger">
                      Are you sure you want to delete {{name}}
                    </nb-alert>
                </nb-card-body>
                <nb-card-footer>
                  <button type='button'  class='push-right' (click)="onConfirmed()" nbButton hero status="danger">Yes</button>
                  <button type='button' class='push-right' nbButton hero status="success" (click)="dismiss()">No, Thank you</button>
                </nb-card-footer>
              </nb-card>`,
  styleUrls: ['delete-modal.component.scss'],
})
export class DeleteModalComponent implements OnInit {
  id: string = '0';
  title: string = '';
  name: string = '';
  action: string = '';
  userType: string = '';
  confirmed: boolean = false;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  constructor(protected ref: NbDialogRef<DeleteModalComponent>) {}
  ngOnInit() {}
  dismiss() {
    this.ref.close({
      id: parseInt(this.id, 10),
      confirmed: this.confirmed,
    });
  }
  onConfirmed() {
    this.confirmed = true;
    this.dismiss();
  }

}
