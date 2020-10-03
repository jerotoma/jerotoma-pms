import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { StudentAcademicLevel, Student, AcademicYear, StudentClassAdmission, ClassView } from 'app/models';
import { StudentAcademicLevelService, AcademicYearService, ClassService } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-student-academic-level-enrollment-show',
  styleUrls: ['./student-academic-level-enrollment-show.component.scss'],
  templateUrl: './student-academic-level-enrollment-show.component.html',
})
export class StudentAcademicLevelEnrollmentShowComponent implements OnInit {
  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Scheduled Courses';
  studentClass: StudentAcademicLevel;
  student: Student;
  academicYearId: number;
  courseId: number;
  jClassIds: number[] = [];
  teacherId: number;
  isLoading: boolean = false;
  studentClassForm: FormGroup;
  academicYear: AcademicYear;
  studentClassAdmission: StudentClassAdmission;
  jClasses: ClassView[];
  academicYears: AcademicYear[];

  constructor(
    private academicYearService: AcademicYearService,
    private classService: ClassService,
    private studentClassService: StudentAcademicLevelService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    ) {
  }
  ngOnInit() {
    this.loadForm();
    this.loadAcademicYears();
    // For one time load
    // let id = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParam => {
        this.loadStudentClass(routeParam.id);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  loadStudentClass(studentId: number) {
    this.studentClassService.getStudentAcademicLevel(studentId)
      .subscribe((studentClass: StudentAcademicLevel ) => {
        if (studentClass) {
          this.studentClass = studentClass;
          this.student = this.studentClass.student;
          this.jClasses = this.studentClass.jClasses;
          this.academicYear = this.studentClass.academicYear;
          this.studentClassForm.patchValue({
              academicYearId: studentClass.academicYear.id,
          }, {emitEvent: false});
        }
      });
  }

  preventDefaultJClass(event: any) {
    event.preventDefault();
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
    .subscribe((academicYears: AcademicYear[] ) => {
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
        if (this.academicYear) {
          this.loadStudentClassesByAcademicYear(this.academicYear.id, this.student.id);
        }
      }
    });
  }

  loadStudentClassesByAcademicYear(academicYearId: number, studentId: number) {
    this.isLoading = true;
    this.classService.loadStudentClassesByAcademicYear(academicYearId, studentId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }

}
