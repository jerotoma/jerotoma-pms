
import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { AcademicYearService } from 'app/services';
import { ShowMessage } from 'app/models';

@Component({
  selector: 'app-academic-year-delete',
  templateUrl: 'academic-year-delete.component.html',
  styleUrls: ['academic-year-delete.component.scss'],
})
export class AcademicYearDeleteComponent implements OnInit {
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
    private academicYearService:  AcademicYearService,
    protected ref: NbDialogRef<AcademicYearDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteAcademicYear() {
    if (this.confirmed) {
    this.academicYearService.deleteAcademicYear(parseInt(this.positionId, 10))
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
    this.deleteAcademicYear();
  }

}
