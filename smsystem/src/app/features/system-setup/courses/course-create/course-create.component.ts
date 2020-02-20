import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { Course, AcademicYear, AcademicDiscipline } from 'app/models';
import { CourseService, AcademicYearService, AcademicDisciplineService } from 'app/services';
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
  @Input() name: string;
  @Input() code: string;
  @Input() id: string;
  @Input() academicYearId: string;
  @Input() description: string;

  courseForm: FormGroup;
  course: Course;
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  academicDisciplines: AcademicDiscipline[] = [];
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';
  isSubmitting: boolean = false;

  constructor(
    private courseService:  CourseService,
    private formBuilder: FormBuilder,
    private academicYearService: AcademicYearService,
    protected academicDisciplineService: AcademicDisciplineService,
    protected ref: NbDialogRef<CourseCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadAcademicYears();
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
      academicYearId: this.academicYearId,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
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
            this.isSubmitting = false;
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
            this.isSubmitting = false;
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

  loadAcademicDisciplineList() {
    this.academicDisciplineService.loadAcademicDisciplineList().subscribe((academicDisciplines: AcademicDiscipline[] ) => {
      if (academicDisciplines) {
        this.showMessage.error = false;
        this.academicDisciplines = academicDisciplines;
      }
    });
  }

  loadAcademicYears() {
    this.academicYearService.getAcademicYears(this.getParam())
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200 && resp.body) {
        const data = resp.body.data;
        this.academicYears = data.academicYears;
        this.patchCourse();
      }
    }, error => {

    });
  }

  loadForm() {
    this.courseForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required],
      code: ['', Validators.required],
      description: [''],
      academicYearId: ['', Validators.required],
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
