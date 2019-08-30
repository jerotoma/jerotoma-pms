import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { AcademicYear } from 'app/models';
import { AcademicYearService} from 'app/services';
import { QueryParam } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-academic-year-create',
  templateUrl: 'academic-year-create.component.html',
  styleUrls: ['academic-year-create.component.scss'],
})
export class AcademicYearCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();

  @Input() name: string = '';
  @Input() code: string = '';
  @Input() id: string = '0';
  @Input() description: string = '';
  @Input() yearOfStudy: string = '';

  academicYearForm: FormGroup;
  academicYear: AcademicYear;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private academicYearService:  AcademicYearService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<AcademicYearCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === 'edit') {
        this.patchPosition();
    }
  }
  patchPosition() {
    this.academicYearForm.patchValue({
      name: this.name,
      description: this.description,
      code: this.code,
      yearOfStudy: this.yearOfStudy,
      id: parseInt(this.id, 10),
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.academicYear = this.academicYearForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updatePosition();
    } else {
      this.academicYearService.createAcademicYear(this.academicYear)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.academicYearForm.reset();
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
          });
    }

  }
  updatePosition() {

    this.academicYearService.updateAcademicYear(this.academicYear)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.academicYearForm.reset();
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
          });
    }
  getDescriptionContent(description: string) {
   if (description) {
    this.academicYearForm.patchValue({
      description: description,
    });
    }
  }

  loadForm() {
    this.academicYearForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required],
      code: ['', Validators.required],
      description: [''],
      yearOfStudy: ['', Validators.required],
    });
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'academicYear',
    };
  }

}
