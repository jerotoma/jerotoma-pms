import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { Course, AcademicYear, AcademicDiscipline } from 'app/models';
import {
  CourseService,
  AcademicYearService,
  AcademicDisciplineService,
  ModalService,
} from 'app/services';
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
  @Input() id: string;


  courseForm: FormGroup;
  course: Course;
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  academicDisciplines: AcademicDiscipline[] = [];
  selectedAcademicDisciplines: number[] = [];
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
    private modalService: ModalService,
    protected ref: NbDialogRef<CourseCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadAcademicYears();
    this.loadAcademicDisciplineList();
    if (this.action === 'edit') {
        this.loadCourse();
    }
  }
  patchCourse() {
    this.courseForm.patchValue({
      name: this.course.name,
      description: this.course.description,
      code: this.course.code,
      id: this.course.id,
      academicYearId: this.course.academicYear.id,
      academicDisciplineIds: this.academicDisciplineService.getAcademicDisciplineIds(this.course.academicDisciplines),
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
          .subscribe((course: Course ) => {
            this.isSubmitting = false;
              if (course) {
                this.course = course;
                this.modalService.openSnackBar('Course ' + course.name + ' has been created', 'success');
                this.dismiss();
              }
          });
    }

  }
  updateCourse() {
    this.courseService.updateCourse(this.course)
        .subscribe((course: Course ) => {
          this.isSubmitting = false;
          if (course) {
            this.course = course;
            this.modalService.openSnackBar('Course ' + course.name + ' has been updated', 'success');
            this.dismiss();
          }
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
      academicDisciplineIds: [null, Validators.required],
    });
  }
  onCheckboxChanged(event: boolean, academicDiscipline: AcademicDiscipline) {
      if (!event) {
        for (let i = 0; i < this.selectedAcademicDisciplines.length; i++) {
          if (this.selectedAcademicDisciplines[i] === academicDiscipline.id) {
            this.selectedAcademicDisciplines.splice(i, 1);
          }
       }
      } else {
        this.selectedAcademicDisciplines.push(academicDiscipline.id);
      }
      this.courseForm.patchValue({
        academicDisciplineIds: this.selectedAcademicDisciplines,
      });
  }

  isChecked(academicDiscipline: AcademicDiscipline) {
    if (this.course && this.course.academicDisciplines && this.course.academicDisciplines.length > 0) {
      return this.course.academicDisciplines.find(acad => acad.id === academicDiscipline.id) !== null;
    }
    return false;
  }

  loadCourse() {
    this.courseService.getCourse(parseInt(this.id, 10)).subscribe((course: Course) => {
      if (course) {
        this.course = course;
        this.selectedAcademicDisciplines = this.academicDisciplineService.getAcademicDisciplineIds(course.academicDisciplines);
        this.patchCourse();
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
      userType: 'course',
    };
  }

}
