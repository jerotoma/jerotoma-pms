
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {  ThemePalette } from '@angular/material/typings';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import {
  StudentClassService,
  ClassService,
  AcademicYearService,
 } from 'app/services';
import {
  ShowMessage,
  ClassView,
  StudentClassAdmission,
  Student,
  AcademicYear,
  StudentClass,
  ClassAdmission,
  ResponseWrapper,
} from 'app/models';
import { QueryParam, USER_TYPE, OPEN_CLOSE_ANIMATION } from 'app/utils';

@Component({
  selector: 'app-student-course-enrollment-edit',
  animations: OPEN_CLOSE_ANIMATION,
  styleUrls: ['./student-course-enrollment-edit.component.scss'],
  templateUrl: './student-course-enrollment-edit.component.html',
})
export class StudentCourseEnrollmentEditComponent implements OnInit {
  @Input() title: string;
  @Input() studentId: string = '0';
  @Output() onCreationSuccess = new EventEmitter();

  userType: string = USER_TYPE.student;
  checkboxColor: ThemePalette = 'warn';

  academicYearId: number;
  courseId: number;
  jClassIds: number[] = [];
  teacherId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  isAddMore: boolean = false;
  academicYear: AcademicYear;
  studentClassAdmission: StudentClassAdmission;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: this.userType,
  };

  registeredJClasses: ClassView[];
  jClasses: ClassView[];
  academicYears: AcademicYear[];
  student: Student;
  students: Student[];
  studentClassForm: FormGroup;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private academicYearService: AcademicYearService,
    private classService: ClassService,
    private studentClassService: StudentClassService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<StudentCourseEnrollmentEditComponent>) {}

  ngOnInit() {
    this.loadData();
    this.loadForm();
    this.loadStudentClasses(parseInt(this.studentId, 10));
  }

  dismiss() {
    this.ref.close({
      confirmed: this.confirmed,
    });
  }

  onSubmit() {
    this.confirmed = false;
    this.studentClassAdmission = this.studentClassForm.value;
    this.updateStudentClass();
  }
  updateStudentClass() {
    this.studentClassService.updateStudentClass(this.studentClassAdmission)
          .subscribe((result: StudentClassAdmission) => {
            const resp = result;
              this.confirmed = true;
              this.studentClassForm.reset();
              this.dismiss();
          });
  }
  removeJClass(event: any, jClass: ClassView, isRemoveJClass: boolean) {
    event.preventDefault();
    event.stopPropagation();
    if (isRemoveJClass) {
      this.checkedChange(false, jClass);
      for (let i = 0; i < this.registeredJClasses.length; i++) {
        if ( this.registeredJClasses[i].id === jClass.id) {
          this.registeredJClasses.splice(i, 1);
        }
     }
    }
  }
  checkedChange(checked: boolean, jClass: ClassView) {
    if (checked) {
      this.jClassIds.push(jClass.id);
    } else {
      for (let i = 0; i < this.jClassIds.length; i++) {
        if ( this.jClassIds[i] === jClass.id) {
          this.jClassIds.splice(i, 1);
        }
     }
    }
    this.studentClassForm.patchValue({
      jClassIds: this.jClassIds,
    });
  }
  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      id: [null],
      academicYearId: ['', Validators.required],
      jClassIds: [[], Validators.required],
      studentId: ['', Validators.required],
      fullName: ['', Validators.required],
    });
  }

  onChanges() {
    this.studentClassForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      if (academicYearId != null) {
        this.academicYears.forEach(academicYear => {
          if (academicYear.id === academicYearId) {
            this.academicYear = academicYear;
          }
        });
        this.loadJClassesByAcademicYear(academicYearId);
      }
    });
  }
  addMoreCourses() {
    this.isAddMore = true;
    if (this.academicYear) {
      this.loadUnregisteredJClassesByStudent(this.academicYear.id, this.student.id);
    }
  }
  loadData() {
    this.loadAcademicYears();
  }

  loadUnregisteredJClassesByStudent(academicYearId: number, studentId: number) {
    this.isLoading = true;
    this.classService.loadUnregisteredJClassesByStudent(academicYearId, studentId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }

  loadJClassesByAcademicYear(academicYearId: number) {
    this.isLoading = true;
    this.classService.loadJClassesByAcademicYear(academicYearId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }

  loadAcademicYears() {
    this.academicYearService.getAcademicYears(this.param)
    .subscribe((academicYears: AcademicYear[]) => {
      if (academicYears) {
        this.academicYears = academicYears;
      }
    });
  }

  loadStudentClasses(studentId: number) {
    this.studentClassService.getStudentClass(studentId).subscribe((studentClass: StudentClass) => {
      this.student = studentClass.student;
      this.academicYear = studentClass.academicYear;
      this.registeredJClasses = studentClass.jClasses;
      this.jClassIds =  this.pushJClasses(this.registeredJClasses),
      this.studentClassForm.patchValue({
        id: studentClass.id,
        studentId: this.student.id,
        academicYearId: this.academicYear.id,
        fullName: this.student.fullName,
        jClassIds: this.jClassIds,
      });
    });
  }
  patchStudent(student: Student) {
    this.student = student;
    this.studentClassForm.patchValue({
      studentId: student.id,
    });
  }

  pushJClasses(jClasses: ClassView[]) {
    const jClassesIds  = [];
    jClasses.forEach((jClass) => {
      jClassesIds.push(jClass.id);
    });
    return jClassesIds;
  }

  removeItemFromArray(jClasses: ClassView[], jClass: ClassView) {
    for (let i = 0; i < this.jClasses.length; i++) {
      if ( this.jClasses[i].id === jClass.id) {
        this.jClasses.splice(i, 1);
      }
   }
   return jClasses;
  }
}
