import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { JClassAdmission } from 'app/models';
import {
  ClassService,
  ClassRoomService,
  AcademicYearService,
  CourseService,
  UserService,
 } from 'app/services';
import { QueryParam } from 'app/utils';
import {
  ShowMessage,
  ClassRoom,
  Teacher,
  Course,
  AcademicYear,
} from 'app/models';

@Component({
  selector: 'app-class-create',
  templateUrl: 'class-create.component.html',
  styleUrls: ['class-create.component.scss'],
})
export class ClassCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();

  @Input() name: string = '';
  @Input() code: string = '';
  @Input() id: string = '0';
  @Input() description: string = '';

  academicYearId: number;
  courseId: number;
  classRoomId: number;
  teacherId: number;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  classRooms: ClassRoom[];
  teachers: Teacher[];
  courses: Course[];
  academicYears: AcademicYear[];

  classForm: FormGroup;
  classAdmission: JClassAdmission;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private classRoomService: ClassRoomService,
    private courseService: CourseService,
    private academicYearService: AcademicYearService,
    private userService: UserService,
    private classService:  ClassService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<ClassCreateComponent>) {}

  ngOnInit() {
    this.loadData();
    this.loadForm();
    if (this.action === 'edit') {
        this.patchClassAdmission();
    }
  }
  patchClassAdmission() {
    this.classForm.patchValue({
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
    this.classAdmission = this.classForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updateClass();
    } else {
      this.classService.createClass(this.classAdmission)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.classForm.reset();
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
  updateClass() {

    this.classService.updateClass(this.classAdmission)
          .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
              this.showMessage.success = true;
              this.showMessage.error = false;
              this.showMessage.message = data  ? data.message : '';
              this.classForm.reset();
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
    this.classForm.patchValue({
      description: description,
    });
    }
  }

  loadForm() {
    this.classForm = this.formBuilder.group({
      id: [null],
      capacity: ['', Validators.required],
      academicYearId: ['', Validators.required],
      courseId: ['', Validators.required],
      teacherId: ['', Validators.required],
      classRoomId: ['', Validators.required],
    });
  }

  loadData() {
    this.loadAcademicYears();
    this.loadClassRooms();
    this.loadCourses();
    this.loadTeachers();
  }

  loadClassRooms() {
    this.classRoomService.getClassRooms(this.param)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200 && resp.body) {
        const data = resp.body.data;
        this.classRooms = data.classRooms;
      }
    }, error => {

    });
  }

  loadCourses() {
    this.courseService.getCourses(this.param)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200 && resp.body) {
        const data = resp.body.data;
        this.courses = data.courses;
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
  loadTeachers() {
    this.userService.loadUsers(this.param)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200 && resp.body) {
        const data = resp.body.data;
        this.teachers = data.teachers;
      }
    }, error => {

    });
  }



}
