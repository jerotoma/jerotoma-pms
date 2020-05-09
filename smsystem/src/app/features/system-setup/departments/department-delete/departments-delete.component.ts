import { Component, OnInit} from '@angular/core';

import { NbDialogRef } from '@nebular/theme';
import { DepartmentService,  ModalService } from 'app/services';
import { ShowMessage } from 'app/models';

@Component({
  selector: 'app-departments-delete',
  templateUrl: 'departments-delete.component.html',
  styleUrls: ['departments-delete.component.scss'],
})
export class DepartmentDeleteComponent implements OnInit {
  departmentId: string = '0';
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
    private departmentService: DepartmentService,
    private modalService: ModalService,
    protected ref: NbDialogRef<DepartmentDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteDepartment() {
    if (this.confirmed) {
      this.departmentService.deleteDepartment(parseInt(this.departmentId, 10))
        .subscribe((result: any ) => {
          if (result) {
            this.dismiss();
            this.modalService.openSnackBar('Department has been deleted', 'success');
          }
      });
    }
  }

  dismiss() {
    this.ref.close();
  }
  onConfirmed() {
    this.confirmed = true;
    this.deleteDepartment();
  }

}
