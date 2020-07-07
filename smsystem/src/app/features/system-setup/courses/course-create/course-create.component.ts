import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { Course, AcademicLevel, Department, Program } from 'app/models';
import {
  CourseService,
  AcademicLevelService,
  ProgramService,
  DepartmentService,
  ModalService,
} from 'app/services';
import { QueryParam, APP_ACTION_TYPE } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-course-create',
  templateUrl: 'course-create.component.html',
  styleUrls: ['course-create.component.scss'],
})
export class CourseCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = APP_ACTION_TYPE.create;
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;


  courseForm: FormGroup;
  course: Course;
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  programs: Program[];
  departments: Department[] = [];
  selectedAcademicDisciplines: number[] = [];
  listDisplay: string = 'none';
  isSubmitting: boolean = false;

  constructor(
    private programService: ProgramService,
    private courseService:  CourseService,
    private formBuilder: FormBuilder,
    private academicLevelService: AcademicLevelService,
    protected departmentService: DepartmentService,
    private modalService: ModalService,
    protected ref: NbDialogRef<CourseCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadDepartmentList();
    this.loadPrograms();
  }

  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.course = this.courseForm.value;
    if (this.action === APP_ACTION_TYPE.edit) {
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
        if (this.action === APP_ACTION_TYPE.edit) {
            this.loadCourse();
        }
        if (this.course) {
          this.patchCourse();
        }
      }
    });
  }

  loadAcademicLevelsByProgramId(programId: number) {
    this.academicLevels = [];
    this.academicLevelService.loadAcademicLevelsByProgramId(programId)
    .subscribe((academicLevels: AcademicLevel[] ) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
        if (this.course) {
          this.patchCourse();
        }
      }
    });
  }

  loadPrograms() {
    this.programService.loadProgramList()
      .subscribe((programs: Program[]) => {
        if (programs) {
          this.programs = programs;
        }
      });
  }

  loadForm() {
    this.courseForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required],
      code: ['', Validators.required],
      description: [''],
      academicLevelId: [null, Validators.required],
      programId: [null, Validators.required],
      departmentId: [null, Validators.required],
    });

    this.courseForm.get('programId').valueChanges.subscribe(programId => {
      if (programId) {
        this.loadAcademicLevelsByProgramId(programId);
      }
    });
  }
  patchCourse() {
    this.courseForm.patchValue({
      id: this.course.id,
      name: this.course.name,
      code: this.course.code,
      description: this.course.description,
      academicLevelId: this.course.academicLevel.id,
      programId: this.course.program.id,
      departmentId: this.course.department.id,
    }, {emitEvent: false});
  }
  loadCourse() {
    this.courseService.getCourse(parseInt(this.id, 10)).subscribe((course: Course) => {
      if (course) {
        this.course = course;
        this.loadAcademicLevelsByProgramId(course.program.id);
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
