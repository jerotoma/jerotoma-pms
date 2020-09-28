import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { ClassView, ClassAdmission, MeetingTime, AcademicLevel, Program } from 'app/models';
import {
  ClassService,
  RoomService,
  AcademicYearService,
  MeetingTimeService,
  CourseService,
  ModalService,
  UserService,
  ProgramService,
  AcademicLevelService,
 } from 'app/services';
import { QueryParam, APP_ACTION_TYPE } from 'app/utils';
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
  @Input() action: string = APP_ACTION_TYPE.create;
  @Output() onCreationSuccess = new EventEmitter();

  @Input() name: string = '';
  @Input() code: string = '';
  @Input() id: string = '0';
  @Input() description: string = '';

  academicYearId: number;
  courseId: number;
  roomId: number;
  teacherId: number;
  programId: number;
  academicLevelId: number;
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
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  programs: Program[];

  classForm: FormGroup;
  classAdmission: ClassAdmission;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private programService: ProgramService,
    private academicLevelService: AcademicLevelService,
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
    this.loadPrograms();
    if (this.action === APP_ACTION_TYPE.edit) {
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
    this.programId = classView.course.program.id;
    this.academicLevelId = classView.course.academicLevel.id;
    this.classForm.patchValue({
      id: classView.id,
      capacity: classView.capacity,
      academicYearId: classView.academicYear.id,
      academicLevelId: classView.course.academicLevel.id,
      programId: classView.course.program.id,
      courseId: classView.course.id,
      teacherId: classView.teacher.id,
      roomId: classView.room.id,
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
    if (this.action === APP_ACTION_TYPE.edit) {
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
        academicLevelId: [null, Validators.required],
        programId: [null, Validators.required],
        courseId: [null, Validators.required],
        teacherId: [null, Validators.required],
        meetingTimeId: [null, Validators.required],
        roomId: [null, Validators.required],
      });
      this.onChanges();
    }

    onChanges(): void {
        this.classForm.get('programId').valueChanges.subscribe(programId => {
          if (programId) {
            this.classForm.patchValue({
                courseId: null,
                teacherId: null,
                academicLevelId: null,
            });
            this.loadAcademicLevelsByProgramId(programId);
          }
        });
        this.classForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
          const programId = this.classForm.get('programId').value;
          if (academicLevelId && programId) {
            this.loadCourses(academicLevelId, programId);
          }
        });
        this.classForm.get('courseId').valueChanges.subscribe((courseId: number) => {
          if (courseId) {
            this.classForm.patchValue({
                teacherId: null,
            });
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
      this.classForm.patchValue({
          roomId: null,
      });
      this.roomService.loadRoomsByCapacity(capacity)
      .subscribe((rooms: Room[]) => {
        if (rooms && rooms.length > 0) {
          this.rooms = rooms;
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
    loadCourses(academicLevelId: number, programId: number) {
      this.courses = [];
      this.classForm.patchValue({
          courseId: null,
          teacherId: null,
      });
      this.courseService.getCoursesByProgramAndAcademicLevelIDs(programId, academicLevelId)
      .subscribe((courses: Course[] ) => {
        if (courses) {
          this.courses = courses;
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
      this.teachers = [];
      this.userService.loadTeachersByCourseID(courseId).subscribe((teachers: Teacher[]) => {
        this.teachers = teachers;
      });
    }
    loadJClassView(jClassViewId: number) {
      this.isLoading = true;
      this.classService.getClass(jClassViewId)
        .subscribe((jClass: ClassView) => {
          this.isLoading = false;
          if (jClass) {
            this.classView = jClass;
            this.loadAcademicLevelsByProgramId(jClass.course.program.id);
            this.loadCourses(jClass.course.academicLevel.id, jClass.course.program.id);
            this.loadTeachersByCourseID(jClass.course.id);
            this.loadRoomByCapacity(jClass.capacity);
            setTimeout(() => {this.patchClassAdmission(jClass); }, 200);
          }
        });
    }

    loadAcademicLevelsByProgramId(programId: number) {
      this.academicLevels = [];
      this.courses = [];
      this.academicLevelService.loadAcademicLevelsByProgramId(programId)
      .subscribe((academicLevels: AcademicLevel[] ) => {
        if (academicLevels) {
          this.academicLevels = academicLevels;
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

    resetForm(isClassCreated: boolean) {
      this.classForm.reset();
      this.dismiss(isClassCreated);
    }

}
