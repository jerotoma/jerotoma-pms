import { Component, OnInit} from '@angular/core';

import { NbDialogRef } from '@nebular/theme';
import { CompletionOrderService,  ModalService } from 'app/services';
import { CompletionOrder,  } from 'app/models';
import { APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-completion-order-delete',
  templateUrl: 'completion-order-delete.component.html',
  styleUrls: ['completion-order-delete.component.scss'],
})
export class CompletionOrderDeleteComponent implements OnInit {
  action: string = APP_ACTION_TYPE.delete;
  completionOrder: CompletionOrder;
  confirmed: boolean = false;

  constructor(
    private courseService: CompletionOrderService,
    private modalService: ModalService,
    protected ref: NbDialogRef<CompletionOrderDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === APP_ACTION_TYPE.delete;
  }

  deleteCompletionOrder() {
    if (this.confirmed) {
    this.courseService.deleteCompletionOrder(this.completionOrder.id)
      .subscribe((result: any ) => {
        if (result) {
         this.modalService.openSnackBar('CompletionOrder has been deleted', 'success');
        }
      });
    }
  }

  dismiss() {
    this.ref.close();
  }
  onConfirmed() {
    this.confirmed = true;
    this.deleteCompletionOrder();
  }

}
