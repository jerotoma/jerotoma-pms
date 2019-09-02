import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { ClassRoom } from 'app/models';
import { SchoolClassService } from 'app/services';
import { QueryParam } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-class-room-create',
  templateUrl: 'class-room-create.component.html',
  styleUrls: ['class-room-create.component.scss'],
})
export class ClassRoomCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();

  @Input() name: string = '';
  @Input() code: string = '';
  @Input() id: string = '0';
  @Input() description: string = '';
  @Input() capacity: string = '';

  schoolClassForm: FormGroup;
  classRoom: ClassRoom;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private schoolClassService:  SchoolClassService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<ClassRoomCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === 'edit') {
        this.patchSchoolClass();
    }
  }
  patchSchoolClass() {
    this.schoolClassForm.patchValue({
      name: this.name,
      description: this.description,
      code: this.code,
      capacity: parseInt(this.capacity, 10),
      id: parseInt(this.id, 10),
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.classRoom = this.schoolClassForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updateSchoolClass();
    } else {
      this.schoolClassService.createSchoolClass(this.classRoom)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.schoolClassForm.reset();
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
  updateSchoolClass() {
    this.schoolClassService.updateSchoolClass(this.classRoom)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.schoolClassForm.reset();
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
    this.schoolClassForm.patchValue({
      description: description,
    });
    }
  }

  loadForm() {
    this.schoolClassForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required],
      code: ['', Validators.required],
      capacity: ['', Validators.compose([Validators.required])],
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
      userType: 'schoolClass',
    };
  }

}
