import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { JClassView, JClassAdmission } from 'app/models';
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
  capacity: number;
  isLoading: boolean = false;
  jClassView: JClassView = null;

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
      this.loadJClassView(parseInt(this.id, 10));
    }
  }
  patchClassAdmission(jClassView: JClassView) {
    this.capacity = jClassView.capacity;
    this.academicYearId = jClassView.academicYear.id;
    this.courseId = jClassView.course.id;
    this.teacherId = jClassView.teacher.id;
    this.classRoomId = jClassView.classRoom.id;
    this.classForm.patchValue({
      id: jClassView.id,
      capacity: jClassView.capacity,
      academicYearId: jClassView.academicYear.id,
      courseId: jClassView.course.id,
      teacherId: jClassView.teacher.id,
      classRoomId: jClassView.classRoom.id,
    });
  }
  dismiss(isClassCreated: boolean) {
    this.ref.close({
      isClassCreated: isClassCreated,
    });
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
              this.resetForm(true);

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
              this.resetForm(true);
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
    loadForm() {
      this.classForm = this.formBuilder.group({
        id: [null],
        capacity: ['', Validators.required],
        academicYearId: ['', Validators.required],
        courseId: ['', Validators.required],
        teacherId: ['', Validators.required],
        classRoomId: ['', Validators.required],
      });
      this.onChanges();
    }

    onChanges(): void {
        this.classForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
          this.loadCourses(academicYearId);
        });

        this.classForm.get('courseId').valueChanges.subscribe((courseId: number) => {
          this.loadTeachersByCourseID(courseId);
        });
    }

    loadData() {
      this.loadAcademicYears();
      this.loadClassRooms();
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
          if (this.jClassView) {
            this.patchClassAdmission(this.jClassView);
          }
        }
      }, error => {

      });
    }

    loadCourses(academicYearId: number) {
      this.courseService.getCoursesByAcademicYearId(academicYearId)
      .subscribe((courses: Course[] ) => {
        if (courses) {
          this.courses = courses;
          if (this.jClassView) {
            this.patchClassAdmission(this.jClassView);
          }
          this.classForm.updateValueAndValidity();
        }
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
          if (this.jClassView) {
            this.patchClassAdmission(this.jClassView);
          }
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
          if (this.jClassView) {
            this.patchClassAdmission(this.jClassView);
          }
        }
      }, error => {

      });
    }
    loadTeachersByCourseID(courseId: number) {
      this.userService.loadTeachersByCourseID(courseId).subscribe((teachers: Teacher[]) => {
        this.teachers = teachers;
      });
    }
    loadJClassView(jClassViewId: number) {
      this.isLoading = true;
      setTimeout( () => {
        this.classService.getClass(jClassViewId)
        .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
          const resp = result;
          const status = resp.status;
          this.isLoading = false;
          if (status !== null && status === 200 && resp.body) {
            this.jClassView = resp.body.data;
            this.patchClassAdmission(this.jClassView);
          }
        }, error => {

        });
      }, 2000);
    }
    resetForm(isClassCreated: boolean) {
      this.classForm.reset();
      this.dismiss(isClassCreated);
    }

}
