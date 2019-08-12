import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { Position } from 'app/models/positions/position.model';
import {PositionService } from 'app/services/positions';
import { QueryParam } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-position-create',
  templateUrl: 'position-create.component.html',
  styleUrls: ['position-create.component.scss'],
})
export class PositionCreateComponent implements OnInit {
  @Input() title: string;
  @Output() onCreationSuccess = new EventEmitter();

  positionForm: FormGroup;
  position: Position;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private positionService:  PositionService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<PositionCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
  }

  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.position = this.positionForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    this.positionService.createPosition(this.position)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.positionForm.reset();
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
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
  getDescriptionContent(description: string) {
   if (description) {
    this.positionForm.patchValue({
      description: description,
    });
    }
  }
  loadForm() {
    this.positionForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required],
      code: ['', Validators.required],
      description: [''],
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
      userType: 'position',
    };
  }

}
