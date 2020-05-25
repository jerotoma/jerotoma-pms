import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { ClassView, ClassAdmission, MeetingTime } from 'app/models';
import {
  ClassService,
  RoomService,
  AcademicYearService,
  MeetingTimeService,
  CourseService,
  ModalService,
  UserService,
 } from 'app/services';
import { QueryParam } from 'app/utils';
import {
  ShowMessage,
  Room,
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
  roomId: number;
  teacherId: number;
  meetingTimeId: number;
  capacity: number;
  isLoading: boolean = false;
  classView: ClassView = null;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  rooms: Room[];
  teachers: Teacher[];
  courses: Course[];
  academicYears: AcademicYear[];
  meetingTimes: MeetingTime[];

  classForm: FormGroup;
  classAdmission: ClassAdmission;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private meetingTimeService: MeetingTimeService,
    private roomService: RoomService,
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

  patchClassAdmission(classView: ClassView) {
    if (!classView) {
      return;
    }
    this.capacity = classView.capacity;
    this.academicYearId = classView.academicYear.id;
    this.courseId = classView.course.id;
    this.teacherId = classView.teacher.id;
    this.meetingTimeId = classView.meetingTime.id;
    this.roomId = classView.room.id;
    this.classForm.patchValue({
      id: classView.id,
      capacity: classView.capacity,
      academicYearId: classView.academicYear.id,
      courseId: classView.course.id,
      teacherId: classView.teacher.id,
      classRoomId: classView.room.id,
      meetingTimeId: classView.meetingTime.id,
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
          .subscribe((result: ClassView ) => {
            if (result) {
              this.modalService.openSnackBar('New class has been created', 'success');
              this.resetForm(true);
            }
          });
    }
  }
  updateClass() {
    this.classService.updateClass(this.classAdmission)
      .subscribe((result: ClassView ) => {
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
        meetingTimeId: [null, Validators.required],
        roomId: [null, Validators.required],
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
        this.classForm.get('capacity').valueChanges.subscribe((capacity: number) => {
          if (capacity) {
            this.loadRoomByCapacity(capacity);
          }
        });
    }

    loadData() {
      this.loadAcademicYears();
      this.loadMeetingTimes();
    }

    loadRoomByCapacity(capacity: number) {
      this.roomService.loadRoomsByCapacity(capacity)
      .subscribe((rooms: Room[]) => {
        if (rooms && rooms.length > 0) {
          this.rooms = rooms;
          this.patchClassAdmission(this.classView);
        } else {
          this.modalService.openSnackBar('No room with ' + capacity + ' capacity was found.', 'info');
        }
      }, error => {

      });
    }
    loadMeetingTimes() {
      this.meetingTimeService.loadMeetingTimes()
      .subscribe((meetingTimes: MeetingTime[]) => {
        if (meetingTimes) {
          this.meetingTimes = meetingTimes;
          this.patchClassAdmission(this.classView);
        }
      }, error => {

      });
    }
    loadCourses(academicYearId: number) {
      this.courseService.getCoursesByAcademicYearId(academicYearId)
      .subscribe((courses: Course[] ) => {
        if (courses) {
          this.courses = courses;
          this.patchClassAdmission(this.classView);
        }
      });
    }
    loadAcademicYears() {
      this.academicYearService.getAcademicYears()
      .subscribe((academicYears: AcademicYear[]) => {
          if (academicYears) {
            this.academicYears = academicYears;
            if (this.classView) {
              this.patchClassAdmission(this.classView);
            }
          }
        });
    }

    loadTeachersByCourseID(courseId: number) {
      this.userService.loadTeachersByCourseID(courseId).subscribe((teachers: Teacher[]) => {
        this.teachers = teachers;
        if (this.classView) {
          setTimeout(() => {this.patchClassAdmission(this.classView); }, 100);
        }
      });
    }
    loadJClassView(jClassViewId: number) {
      this.isLoading = true;
      this.classService.getClass(jClassViewId)
        .subscribe((jClass: ClassView) => {
          this.isLoading = false;
          if (jClass) {
            this.classView = jClass;
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
