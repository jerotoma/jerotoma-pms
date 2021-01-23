import { Component, OnInit, Input } from '@angular/core';
import { NbDialogService } from '@nebular/theme';
import { AcademicLevelClassesEnrollmentCreateComponent } from 'app/shared/enrollments/classes';
import {
  StudentAcademicLevelService,
  AcademicYearService,
  ClassService,
  UserService,
  AcademicLevelService,
 } from 'app/services';
import { USER_TYPE, QueryParam } from 'app/utils';
import {
  ClassView,
  Student,
  Teacher,
  AcademicLevel,
  AcademicYear,
  StudentAcademicLevelClass,
} from 'app/models';

@Component({
  selector: 'app-my-course',
  templateUrl: './my-course.component.html',
  styleUrls: ['./my-course.component.scss'],
})
export class MyCourseComponent implements OnInit {

  @Input('userType') userType = USER_TYPE.STUDENT;
  @Input('user') user: Student | Teacher | any = {};
  @Input('title') title: string = 'Registered Classes';
  @Input('programId') programId: number = null;

  teacherId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  academicLevel: AcademicLevel;
  studentAcademicLevelClasses: StudentAcademicLevelClass[];
  academicYear: AcademicYear;
  classViews: ClassView[];
  academicLevels: AcademicLevel[];
  student: Student;
  students: Student[];

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType:  this.userType,
  };

  constructor(
    private academicLevelService: AcademicLevelService,
    private academicYearService: AcademicYearService,
    private userService: UserService,
    private classService: ClassService,
    private dialogService: NbDialogService,
    private studentAcademicLevelService: StudentAcademicLevelService) { }

  ngOnInit() {
    if (this.user.userId && this.user.userType === USER_TYPE.STUDENT) {
      this.loadAllStudentAcademicLevelsClassList(this.user.userId);
      this.student = this.user;
    } else if (this.user.userId && this.user.userType === USER_TYPE.TEACHER) {
      this.loadTeacherClasses(this.user.userId);
    }
  }

  loadTeacherClasses(userId: number) {
    this.classService.loadClassesByUserId(userId).subscribe((classViews: ClassView[]) => {
      this.classViews = classViews;
    });
  }

  getCurrentAcademicYear() {
    this.academicYearService.getCurrentAcademicYear()
    .subscribe((academicYear: AcademicYear) => {
      this.isLoading = false;
      if (academicYear) {
        this.academicYear = academicYear;
      }
    });
  }

  loadAllStudentAcademicLevelsClassList(studentId: number) {
    this.isLoading = true;
    this.classService.loadAllStudentAcademicLevelsClassList(studentId).subscribe((studentAcademicLevelClasses: StudentAcademicLevelClass[]) => {
      this.isLoading = false;
      this.studentAcademicLevelClasses = studentAcademicLevelClasses;
    });
  }

  loadAcademicLevels() {
    this.academicLevelService.loadAcademicLevelList()
    .subscribe((academicLevels: AcademicLevel[]) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
      }
    });
  }

  setCurrentAcademicLevel(academicLevelId: number) {
    this.academicLevels.forEach(academicLevel => {
      if (academicLevel.id === academicLevelId) {
        this.academicLevel = academicLevel;
      }
    });
  }

  loadJClassesByAcademicLevel(academicLevelId: number, studentId: number) {
    this.isLoading = true;
    this.studentAcademicLevelService.loadClassesByStudentIDAndAcademicLevelID(academicLevelId, studentId).subscribe((jClassViews: ClassView[]) => {
      this.classViews = jClassViews;
      this.isLoading = false;
    });
  }

  addStudentCourse() {
    this.dialogService.open(AcademicLevelClassesEnrollmentCreateComponent, {
      context: {
        title: 'Enroll New Classes',
        action: 'create',
        student: this.student,
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {

      }
    });
  }

  preventDefaultJClass(event: any) {
    event.preventDefault();
  }

  get isUserTeacher() {
    return this.userType === USER_TYPE.TEACHER;
  }

  get isUserStudent() {
    return this.userType === USER_TYPE.STUDENT;
  }
}
