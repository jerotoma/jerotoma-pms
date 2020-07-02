import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { Department, ResponseWrapper } from 'app/models';
import {
  DepartmentService,
  ModalService,
} from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-department-create',
  templateUrl: 'department-create.component.html',
  styleUrls: ['department-create.component.scss'],
})
export class DepartmentCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;


  departmentForm: FormGroup;
  department: Department;
  listDisplay: string = 'none';
  isSubmitting: boolean = false;

  constructor(
    private departmentService:  DepartmentService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<DepartmentCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === 'edit') {
        this.loadDepartment();
    }
  }
  patchDepartment() {
    this.departmentForm.patchValue({
      name: this.department.name,
      id: this.department.id,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.department = this.departmentForm.value;
        if (this.action === 'edit') {
            this.updateDepartment();
        } else {
          this.departmentService.createDepartment(this.department)
              .subscribe((department: Department ) => {
                this.isSubmitting = false;
                  if (department) {
                    this.department = department;
                    this.modalService.openSnackBar('Department ' + department.name + ' has been created', 'success');
                    this.dismiss();
                  }
              });
        }

  }
  updateDepartment() {
    this.departmentService.updateDepartment(this.department)
        .subscribe((department: Department ) => {
          this.isSubmitting = false;
          if (department) {
            this.department = department;
            this.modalService.openSnackBar('Department ' + department.name + ' has been updated', 'success');
            this.dismiss();
          }
      });
    }
  getDescriptionContent(description: string) {
   if (description) {
      this.departmentForm.patchValue({
        description: description,
      });
    }
  }

  loadForm() {
    this.departmentForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required ],
    });
  }

  loadDepartment() {
    this.departmentService.getDepartment(parseInt(this.id, 10)).subscribe((department: Department) => {
      if (department) {
        this.department = department;
        this.patchDepartment();
      }
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
      userType: 'department',
    };
  }
}
