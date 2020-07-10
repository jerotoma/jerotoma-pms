import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import {
  StudentClassService,
  ClassService,
  AcademicYearService,
 } from 'app/services';
import { USER_TYPE, QueryParam } from 'app/utils';
import {
  ShowMessage,
  ClassView,
  StudentClassAdmission,
  Student,
  AcademicYear,
  StudentClass,
  ResponseWrapper,
} from 'app/models';

@Component({
  selector: 'app-my-course',
  templateUrl: './my-course.component.html',
  styleUrls: ['./my-course.component.scss'],
})
export class MyCourseComponent implements OnInit {

  @Input('userType') userType = USER_TYPE.student;
  @Input('userId') userId: number = null;

  academicYearId: number;
  courseId: number;
  jClassIds: number[] = [];
  teacherId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  academicYear: AcademicYear;
  jClasses: ClassView[];
  academicYears: AcademicYear[];
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
    private academicYearService: AcademicYearService,
    private classService: ClassService,
    private studentClassService: StudentClassService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.loadForm();
    if (this.userId && this.userType === USER_TYPE.student) {
      this.loadAcademicYears();
      this.loadStudentClasses();
    }
  }

  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      id: [null],
      academicYearId: ['', Validators.required],
      jClassIds: [[], Validators.required],
      studentId: ['', Validators.required],
      fullName: ['', Validators.required],
    });
    this.onChanges();
  }

  loadAcademicYears() {
    this.academicYearService.getAcademicYears()
    .subscribe((academicYears: AcademicYear[]) => {
      if (academicYears) {
        this.academicYears = academicYears;
      }
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
        if (this.academicYear && this.userType === USER_TYPE.student) {
          this.loadStudentJClassesByAcademicYear(this.academicYear.id, this.userId);
        }
      }
    });
  }

  loadStudentJClassesByAcademicYear(academicYearId: number, studentId: number) {
    this.isLoading = true;
    this.classService.loadStudentClassesByAcademicYear(academicYearId, studentId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }

  loadStudentClasses() {
    this.studentClassService.getStudentClassesByStudentId(this.userId).subscribe((studentClasses: StudentClass[]) => {
      if (studentClasses && studentClasses.length > 0) {
        const studentClass = studentClasses[0];
        this.student = studentClass.student;
        this.academicYear = studentClass.academicYear;
        this.loadJClassesByAcademicYear(studentClass.academicYear.id);
        this.jClasses = studentClass.jClasses;
        this.studentClassForm.patchValue({
          id: studentClass.id,
          studentId: this.student.id,
          academicYearId: this.academicYear.id,
        }, {emitEvent: false});
      }
    });
  }

  patchStudent(student: Student) {
    this.student = student;
    this.studentClassForm.patchValue({
      studentId: student.id,
    });
  }

  loadJClassesByAcademicYear(academicYearId: number) {
    this.isLoading = true;
    this.classService.loadJClassesByAcademicYear(academicYearId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }

  preventDefaultJClass(event: any) {
    event.preventDefault();
  }
}
