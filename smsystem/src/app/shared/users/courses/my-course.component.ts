import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
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
  AcademicLevel,
  AcademicYear,
  StudentAcademicLevel,
} from 'app/models';

@Component({
  selector: 'app-my-course',
  templateUrl: './my-course.component.html',
  styleUrls: ['./my-course.component.scss'],
})
export class MyCourseComponent implements OnInit {

  @Input('userType') userType = USER_TYPE.STUDENT;
  @Input('userId') userId: number = null;
  @Input('programId') programId: number = null;

  academicLevelId: number;
  courseId: number;
  jClassIds: number[] = [];
  teacherId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  academicLevel: AcademicLevel;
  classViews: ClassView[];
  academicLevels: AcademicLevel[];
  student: Student;
  students: Student[];
  studentClassForm: FormGroup;

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
    private studentAcademicLevelService: StudentAcademicLevelService,
    private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.loadForm();
    if (this.userId && this.userType === USER_TYPE.STUDENT) {
      this.loadStudent();
    } else if (this.userId && this.userType === USER_TYPE.TEACHER) {
      this.loadTeacherClasses(this.userId);
    }
  }

  loadTeacherClasses(userId: number) {
    this.classService.loadClassesByUserId(userId).subscribe((classViews: ClassView[]) => {
      this.classViews = classViews;
    });
  }

  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      id: [null],
      academicLevelId: ['', Validators.required],
      jClassIds: [[], Validators.required],
      studentId: ['', Validators.required],
      fullName: ['', Validators.required],
    });
    this.onChanges();
  }

  loadStudent() {
    this.userService.loadUser(this.userId).subscribe((student: Student) => {
      this.student = student;
      this.loadAcademicLevelsByProgramId(this.student.programId);
      this.getCurrentAcademicYear();
    });
  }

  getCurrentAcademicYear() {
    this.academicYearService.getCurrentAcademicYear()
    .subscribe((academicYear: AcademicYear) => {
      this.isLoading = false;
      if (academicYear) {
        this.loadStudentClasses(this.student.id, this.student.academicLevelId, academicYear.id);
      }
    });
  }

  loadStudentClasses(studentId: number, academicLevelId: number, academicYearId: number) {
    this.isLoading = true;
    this.classService.loadStudentRegisteredClasses(studentId, academicLevelId, academicYearId).subscribe((classViews: ClassView[]) => {
      this.classViews = classViews;
      this.isLoading = false;
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

  onChanges() {
    this.studentClassForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      if (academicLevelId != null) {
        this.setCurrentAcademicLevel(academicLevelId);
        if (this.academicLevel && this.userType === USER_TYPE.STUDENT) {
          this.loadJClassesByAcademicLevel(this.academicLevel.id, this.student.id);
        }
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

  patchStudent(student: Student) {
    this.student = student;
    this.studentClassForm.patchValue({
      studentId: student.id,
    });
  }

  loadAcademicLevelsByProgramId(programId: number) {
    this.academicLevels = [];
    this.academicLevelService.loadAcademicLevelsByProgramId(programId)
    .subscribe((academicLevels: AcademicLevel[] ) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
        this.setCurrentAcademicLevel(this.academicLevel.id);
        this.studentClassForm.patchValue({
          academicLevelId:  this.academicLevel.id,
        }, {emitEvent: false});
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
