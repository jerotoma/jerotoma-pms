import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { Course } from 'app/models';
import { CourseService } from 'app/services';
import { QueryParam } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-course-create',
  templateUrl: 'course-create.component.html',
  styleUrls: ['course-create.component.scss'],
})
export class CourseCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();

  @Input() name: string = '';
  @Input() code: string = '';
  @Input() id: string = '0';
  @Input() description: string = '';

  courseForm: FormGroup;
  course: Course;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private courseService:  CourseService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<CourseCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === 'edit') {
        this.patchCourse();
    }
  }
  patchCourse() {
    this.courseForm.patchValue({
      name: this.name,
      description: this.description,
      code: this.code,
      id: parseInt(this.id, 10),
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.course = this.courseForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updateCourse();
    } else {
      this.courseService.createCourse(this.course)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.courseForm.reset();
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
  updateCourse() {

    this.courseService.updateCourse(this.course)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.courseForm.reset();
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
    this.courseForm.patchValue({
      description: description,
    });
    }
  }

  loadForm() {
    this.courseForm = this.formBuilder.group({
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
      userType: 'course',
    };
  }

}
