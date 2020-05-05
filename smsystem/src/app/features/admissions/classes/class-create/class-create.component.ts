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
  ModalService,
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
    private modalService: ModalService,
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
    if (!jClassView) {
      return;
    }
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
    }, {emitEvent: false});
  }

  dismiss(isClassCreated: boolean) {
    this.ref.close({
      isClassCreated: isClassCreated,
    });
  }

  onSubmit() {
    this.classAdmission = this.classForm.value;
    if (this.action === 'edit') {
      this.updateClass();
    } else {
      this.classService.createClass(this.classAdmission)
          .subscribe((result: JClassView ) => {
            if (result) {
              this.modalService.openSnackBar('New class has been created', 'success');
              this.resetForm(true);
            }
          });
    }
  }
  updateClass() {
    this.classService.updateClass(this.classAdmission)
      .subscribe((result: JClassView ) => {
        if (result) {
          this.modalService.openSnackBar('Class has been updated', 'success');
          this.resetForm(true);
        }
      });
    }
    loadForm() {
      this.classForm = this.formBuilder.group({
        id: [null],
        capacity: [null, Validators.required],
        academicYearId: [null, Validators.required],
        courseId: [null, Validators.required],
        teacherId: [null, Validators.required],
        classRoomId: [null, Validators.required],
      });
      this.onChanges();
    }

    onChanges(): void {
        this.classForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
          if (academicYearId) {
            this.loadCourses(academicYearId);
          }
        });
        this.classForm.get('courseId').valueChanges.subscribe((courseId: number) => {
          if (courseId) {
            this.loadTeachersByCourseID(courseId);
          }
        });
    }

    loadData() {
      this.loadAcademicYears();
      this.loadClassRooms();
    }

    loadClassRooms() {
      this.classRoomService.getClassRooms(this.param)
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200 && resp.body) {
          const data = resp.body.data;
          this.classRooms = data.classRooms;
          this.patchClassAdmission(this.jClassView);
        }
      }, error => {

      });
    }

    loadCourses(academicYearId: number) {
      this.courseService.getCoursesByAcademicYearId(academicYearId)
      .subscribe((courses: Course[] ) => {
        if (courses) {
          this.courses = courses;
          this.patchClassAdmission(this.jClassView);
        }
      });
    }
    loadAcademicYears() {
      this.academicYearService.getAcademicYears(this.param)
      .subscribe((academicYears: AcademicYear[]) => {
          if (academicYears) {
            this.academicYears = academicYears;
            if (this.jClassView) {
              this.patchClassAdmission(this.jClassView);
            }
          }
        });
    }

    loadTeachersByCourseID(courseId: number) {
      this.userService.loadTeachersByCourseID(courseId).subscribe((teachers: Teacher[]) => {
        this.teachers = teachers;
        if (this.jClassView) {
          setTimeout(() => {this.patchClassAdmission(this.jClassView); }, 100);
        }
      });
    }
    loadJClassView(jClassViewId: number) {
      this.isLoading = true;
      this.classService.getClass(jClassViewId)
        .subscribe((jClass: JClassView) => {
          this.isLoading = false;
          if (jClass) {
            this.jClassView = jClass;
            this.loadCourses(jClass.academicYear.id);
            this.loadTeachersByCourseID(jClass.course.id);
            this.patchClassAdmission(jClass);
          }
        });
    }
    resetForm(isClassCreated: boolean) {
      this.classForm.reset();
      this.dismiss(isClassCreated);
    }

}
