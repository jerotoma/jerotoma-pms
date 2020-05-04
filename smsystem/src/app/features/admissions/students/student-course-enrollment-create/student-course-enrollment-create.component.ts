
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { JClassAdmission } from 'app/models';
import {
  StudentClassService,
  ClassService,
  AcademicYearService,
  UserService,
 } from 'app/services';
import { QueryParam, USER_TYPE } from 'app/utils';
import {
  ShowMessage,
  JClassView,
  StudentClassAdmission,
  Student,
  AcademicYear,
  StudentClass,
  ResponseWrapper,
} from 'app/models';

@Component({
  selector: 'app-student-course-enrollment-create',
  styleUrls: ['./student-course-enrollment-create.component.scss'],
  templateUrl: './student-course-enrollment-create.component.html',
})
export class StudentCourseEnrollmentCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Input() studentId: string = '0';
  @Output() onCreationSuccess = new EventEmitter();

  userType: string = USER_TYPE.student;

  academicYearId: number;
  courseId: number;
  jClassIds: number[] = [];
  teacherId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  academicYear: AcademicYear;
  studentClassAdmission: StudentClassAdmission;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType:  this.userType,
  };

  jClasses: JClassView[];
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
    protected ref: NbDialogRef<StudentCourseEnrollmentCreateComponent>) {}

  ngOnInit() {
    this.loadData();
    this.loadForm();
  }

  dismiss() {
    this.ref.close({
      confirmed: this.confirmed,
    });
  }

  onSubmit() {
    this.confirmed = false;
    this.studentClassAdmission = this.studentClassForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    this.studentClassService.createStudentClass(this.studentClassAdmission)
    .subscribe((result: StudentClassAdmission) => {
      const resp = result;
      this.confirmed = true;
      this.studentClassForm.reset();
      this.dismiss();
    });
  }

  checkedChange(checked: boolean, jClass: JClassView) {
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
    });
    this.onChanges();
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

  loadData() {
    this.loadAcademicYears();
  }

  loadJClassesByAcademicYear(academicYearId: number) {
    this.isLoading = true;
    this.classService.loadJClassesByAcademicYear(academicYearId).subscribe((jClassViews: JClassView[]) => {
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
  loadStudents(studentId: number) {
    this.studentClassService.getStudentClass(studentId).subscribe((studentClass: StudentClass) => {
      this.student = studentClass.student;
      this.academicYear = studentClass.academicYear;
      this.loadJClassesByAcademicYear(studentClass.academicYear.id);
      this.jClasses = studentClass.jClasses;
      this.studentClassForm.patchValue({
        id: studentClass.id,
        studentId: this.student.id,
        academicYearId: this.academicYear.id,
        jClassIds: this.pushJClasses(this.jClasses),
      }, {emitEvent: false});
    });
  }
  patchStudent(student: Student) {
    this.student = student;
    this.studentClassForm.patchValue({
      studentId: student.id,
    });
  }

  pushJClasses(jClasses: JClassView[]) {
    const jClassesIds  = [];
    jClasses.forEach((jClass) => {
      jClassesIds.push(jClass.id);
    });
    return jClassesIds;
  }
}
