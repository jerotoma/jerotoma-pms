import { Component, OnInit, Input} from '@angular/core';
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
  @Input('id') id: string = '0';
  @Input('title') title: string = '';
  @Input('name') name: string = '';
  @Input('action') action: string = '';
  @Input('userType') userType: string = '';
  @Input('confirmed') confirmed: boolean = false;

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
