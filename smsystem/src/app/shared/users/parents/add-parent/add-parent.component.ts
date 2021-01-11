import { Component, OnInit, ViewChild, Output, Input, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';

import { ParentCreateComponent } from '../create/parent-create.component';
import { ParentWrapper, ResponseWrapper, Student, Parent } from 'app/models';
import { UserService, ModalService } from 'app/services';
import { DateFormatter, USER_TYPE, APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-add-parent',
  templateUrl: 'add-parent.component.html',
  styleUrls: ['add-parent.component.scss'],
})
export class AddParentComponent implements OnInit {

  @Input('student') student: Student;
  @Input('title') title: string = 'Create New Parent';
  @ViewChild(ParentCreateComponent, {static: false}) appParent: ParentCreateComponent;

  parentForm: FormGroup;
  parent: Parent;
  action: string = APP_ACTION_TYPE.create;
  userType: string = USER_TYPE.PARENT;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<AddParentComponent>,
    ) {}

  ngOnInit() {
    this.loadParentForm();
  }

  loadParentForm() {
    this.parentForm = this.formBuilder.group({
      parent: [null, Validators.required],
      userType: [this.userType, Validators.required],
    });
  }

  onParentFormChanges(parentWrapper: ParentWrapper) {
    if (!parentWrapper) {
      return;
    }
    if (!parentWrapper.valid) {
      this.parentForm.controls['parent'].setErrors({ invalidForm: true });
    } else {
      this.parentForm.controls['parent'].setErrors(null);
      this.parent = parentWrapper.parent;
      this.parent.studentId = this.student.id;
      this.parentForm.patchValue({parent: this.parent});
    }
  }

  onSubmitParentForm() {
    if (!this.parentForm.valid ) {
      return;
    }
    this.userService.addUser(this.parentForm.value).subscribe((resp: ResponseWrapper) => {
      if (resp && resp.success) {
        this.modalService.openSnackBar('New Parent has been created', 'success');
        this.resetForms();
      }
    });
  }

  resetForms() {
    this.parentForm.reset();
    this.appParent.resetForms();
    this.dismiss();
  }

  dismiss() {
    this.ref.close({
      confirmed: true,
    });
  }
}
