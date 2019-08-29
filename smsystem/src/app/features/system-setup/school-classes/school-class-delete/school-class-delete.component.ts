import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { AcademicDisciplineService } from 'app/services/academic-disciplines/academic-discipline.service';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-school-class-delete',
  templateUrl: 'school-class-delete.component.html',
  styleUrls: ['school-class-delete.component.scss'],
})
export class SchoolClassDeleteComponent implements OnInit {
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
    private academicDisciplineService: AcademicDisciplineService,
    protected ref: NbDialogRef<SchoolClassDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteAcademicDiscipline() {
    if (this.confirmed) {
    this.academicDisciplineService.deleteAcademicDiscipline(parseInt(this.positionId, 10)).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
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
    this.deleteAcademicDiscipline();
  }

}
