import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { CompletionOrder, AcademicLevel, Department, Program } from 'app/models';
import {
  CompletionOrderService,
  AcademicLevelService,
  ProgramService,
  DepartmentService,
  ModalService,
} from 'app/services';
import { QueryParam, APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-completion-order-create',
  templateUrl: 'completion-order-create.component.html',
  styleUrls: ['completion-order-create.component.scss'],
})
export class CompletionOrderCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = APP_ACTION_TYPE.create;
  @Output() onCreationSuccess = new EventEmitter();
  @Input() completionOrder:  CompletionOrder;

  completionOrderForm: FormGroup;
  listDisplay: string = 'none';
  isSubmitting: boolean = false;

  constructor(
    private completionOrderService:  CompletionOrderService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<CompletionOrderCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.completionOrder && this.action === APP_ACTION_TYPE.edit) {
      this.patchCompletionOrder();
    }
  }

  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.completionOrder = this.completionOrderForm.value;
    if (this.action === APP_ACTION_TYPE.edit) {
      this.updateCompletionOrder();
    } else {
      this.completionOrderService.createCompletionOrder(this.completionOrder)
          .subscribe((completionOrder: CompletionOrder ) => {
            this.isSubmitting = false;
              if (completionOrder) {
                this.completionOrder = completionOrder;
                this.modalService.openSnackBar('Completion Order ' + completionOrder.name + ' has been created', 'success');
                this.dismiss();
              }
          });
    }

  }

  updateCompletionOrder() {
    this.completionOrderService.updateCompletionOrder(this.completionOrder)
        .subscribe((completionOrder: CompletionOrder ) => {
          this.isSubmitting = false;
          if (completionOrder) {
            this.completionOrder = completionOrder;
            this.modalService.openSnackBar('Completion Order ' + completionOrder.name + ' has been updated', 'success');
            this.dismiss();
          }
      });
  }

  loadForm() {
    this.completionOrderForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required],
      completionOrder: ['', Validators.required],
    });
  }

  patchCompletionOrder() {
    this.completionOrderForm.patchValue({
      id: this.completionOrder.id,
      name: this.completionOrder.name,
      completionOrder: this.completionOrder.completionOrder,
    }, {emitEvent: false});
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'completionOrder',
    };
  }
}
