import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { Course, AcademicYear, Department } from 'app/models';
import {
  CourseService,
  AcademicYearService,
  DepartmentService,
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
  departments: Department[] = [];
  selectedAcademicDisciplines: number[] = [];
  listDisplay: string = 'none';
  isSubmitting: boolean = false;

  constructor(
    private courseService:  CourseService,
    private formBuilder: FormBuilder,
    private academicYearService: AcademicYearService,
    protected departmentService: DepartmentService,
    private modalService: ModalService,
    protected ref: NbDialogRef<CourseCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadAcademicYears();
    this.loadDepartmentList();
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
      departmentId: this.course.department.id,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.course = this.courseForm.value;
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

  loadDepartmentList() {
    this.departmentService.loadDepartmentList().subscribe((departments: Department[] ) => {
      if (departments) {
        this.departments = departments;
        if (this.course) {
          this.patchCourse();
        }
      }
    });
  }

  loadAcademicYears() {
    this.academicYearService.getAcademicYears(this.getParam())
    .subscribe((academicYears: AcademicYear[] ) => {
      if (academicYears) {
        this.academicYears = academicYears;
        if (this.course) {
          this.patchCourse();
        }
      }
    });
  }

  loadForm() {
    this.courseForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required],
      code: ['', Validators.required],
      description: [''],
      academicYearId: ['', Validators.required],
      departmentId: [null, Validators.required],
    });
  }
  loadCourse() {
    this.courseService.getCourse(parseInt(this.id, 10)).subscribe((course: Course) => {
      if (course) {
        this.course = course;
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
