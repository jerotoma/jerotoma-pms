import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { StudentAcademicLevel, Student, AcademicYear, StudentClassAdmission, ClassView } from 'app/models';
import { StudentAcademicLevelService, AcademicYearService, ClassService } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-student-academic-level-classes-enrollment-show',
  styleUrls: ['./student-academic-level-classes-enrollment-show.component.scss'],
  templateUrl: './student-academic-level-classes-enrollment-show.component.html',
})
export class StudentAcademicLevelClassesEnrollmentShowComponent implements OnInit {
  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Scheduled Classes';
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
    private studentAcademicLevelService: StudentAcademicLevelService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    ) {
  }
  ngOnInit() {
    this.loadForm();
    // For one time load
    // let id = this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(routeParam => {
        this.loadStudentAcademicLevel(routeParam.id);
    });
    this.route.queryParams.subscribe(queryParams => {
      // do something with the query params
    });
  }

  loadStudentAcademicLevel(studentAcademicLevelId: number) {
    this.studentAcademicLevelService.getStudentAcademicLevel(studentAcademicLevelId)
      .subscribe((studentAcademicLevel: StudentAcademicLevel ) => {
        if (studentAcademicLevel) {
          this.studentClass = studentAcademicLevel;
          this.student = this.studentClass.student;
          this.jClasses = this.studentClass.jClasses;
          this.academicYear = this.studentClass.academicYear;
          this.loadAcademicYears(studentAcademicLevel.student.id, studentAcademicLevel.academicLevel.id);
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

  loadAcademicYears(studentId: number, academicLevelId: number) {
    this.academicYearService.findAcademicYearsByStudentLevel(studentId, academicLevelId)
    .subscribe((academicYears: AcademicYear[] ) => {
      if (academicYears) {
        this.academicYears = academicYears;
        this.studentClassForm.patchValue({
          academicYearId:  this.academicYear.id,
      }, {emitEvent: false});
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
          this.loadStudentClassesByAcademicYear(this.student.id, this.student.academicLevelId, this.academicYear.id);
        }
      }
    });
  }

  loadStudentClassesByAcademicYear(studentId: number, academicLevelId: number, academicYearId: number) {
    this.isLoading = true;
    this.classService.loadStudentRegisteredClasses(studentId, academicLevelId, academicYearId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }

}
