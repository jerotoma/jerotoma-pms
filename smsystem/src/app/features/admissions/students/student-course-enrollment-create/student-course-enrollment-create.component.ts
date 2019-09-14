
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
import { QueryParam } from 'app/utils';
import {
  ShowMessage,
  JClassView,
  StudentClassAdmission,
  Student,
  AcademicYear,
} from 'app/models';

@Component({
  selector: 'app-student-course-enrollment-create',
  styleUrls: ['./student-course-enrollment-create.component.scss'],
  templateUrl: './student-course-enrollment-create.component.html',
})
export class StudentCourseEnrollmentCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();


  @Input() studentId: string = '0';

  academicYearId: number;
  courseId: number;
  jClassIds: number[] = [];
  teacherId: number;
  studentClassAdmission: StudentClassAdmission;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  jClasses: JClassView[];
  academicYears: AcademicYear[];
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
    private userService: UserService,
    private classService: ClassService,
    private studentClassService: StudentClassService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<StudentCourseEnrollmentCreateComponent>) {}

  ngOnInit() {
    this.loadData();
    this.loadForm();
    if (this.action === 'edit') {
        this.patchStudentClass();
    }
  }
  patchStudentClass() {
    this.studentClassForm.patchValue({

    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.studentClassAdmission = this.studentClassForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updateStudentClass();
    } else {
      this.studentClassService.createStudentClass(this.studentClassAdmission)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.studentClassForm.reset();
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
  updateStudentClass() {
    this.studentClassService.updateStudentClass(this.studentClassAdmission)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.studentClassForm.reset();
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
    window.console.log(this.jClassIds);
  }

  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      id: [null],
      academicYearId: ['', Validators.required],
      jClassIds: [[], Validators.required],
      studentId: ['', Validators.required],
    });
  }

  loadData() {
    this.loadAcademicYears();
    this.loadJClasses();
    this.loadStudents();
  }

  loadJClasses() {
    this.param.userType = 'student';
    this.classService.getClasses(this.param)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200 && resp.body) {
        const data = resp.body.data;
        this.jClasses = data.jClasses;
      }
    }, error => {

    });
  }
  loadAcademicYears() {
    this.academicYearService.getAcademicYears(this.param)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200 && resp.body) {
        const data = resp.body.data;
        this.academicYears = data.academicYears;
      }
    }, error => {

    });
  }
  loadStudents() {
    this.userService.loadUsers(this.param)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200 && resp.body) {
        const data = resp.body.data;
        this.students = data.students;
      }
    }, error => {

    });
  }



}
