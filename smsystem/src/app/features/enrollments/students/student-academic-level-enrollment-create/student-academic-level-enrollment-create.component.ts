
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray, FormControl } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import {
  StudentAcademicLevelService,
  ClassService,
  AcademicYearService,
  ProgramService,
  AcademicLevelService,
  UserService,
  ModalService,
 } from 'app/services';
import { QueryParam, USER_TYPE, APP_CONSTANTS } from 'app/utils';
import {
  ShowMessage,
  ClassView,
  StudentClassAdmission,
  Student,
  AcademicYear,
  StudentAcademicLevel,
  AcademicLevel,
  Program,
} from 'app/models';

@Component({
  selector: 'app-student-academic-level-enrollment-create',
  styleUrls: ['./student-academic-level-enrollment-create.component.scss'],
  templateUrl: './student-academic-level-enrollment-create.component.html',
})
export class StudentCourseEnrollmentCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Input() studentId: string = '0';
  @Output() onCreationSuccess = new EventEmitter();
  currentAcademicYearKey: string = APP_CONSTANTS.currentAcademicYear;

  userType: string = USER_TYPE.STUDENT;

  academicYearId: number;
  courseId: number;
  programId: number;
  academicLevelId: number;
  jClassIds: number[] = [];
  teacherId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  checkAllStudents: boolean = false;
  checkAllCourses: boolean = false;
  academicYear: AcademicYear;
  currentAcademicYear: AcademicYear = null;
  studentClassAdmission: StudentClassAdmission;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType:  this.userType,
  };

  jClasses: ClassView[];
  academicYears: AcademicYear[];
  student: Student;
  studentClassForm: FormGroup;
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  programs: Program[];
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private modalService: ModalService,
    private programService: ProgramService,
    private academicLevelService: AcademicLevelService,
    private academicYearService: AcademicYearService,
    private classService: ClassService,
    private studentClassService: StudentAcademicLevelService,
    private studentAcademicLevelService: StudentAcademicLevelService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<StudentCourseEnrollmentCreateComponent>) {}

  ngOnInit() {
    this.loadData();
    this.loadForm();
    this.loadPrograms();
  }

  dismiss() {
    this.ref.close({
      confirmed: this.confirmed,
    });
  }

  checkAllStudentBoxes(checked: boolean) {
    this.checkAllStudents = checked;
  }

  checkAllCourseBoxes(checked: boolean) {
    this.checkAllCourses = checked;
  }

  setSelectedUser(student: Student) {
    this.studentClassForm.patchValue({
      studentId: student.id,
    });
    this.programId = student.programId;
    this.student = student;
    this.loadAvailableAcademicLevelsByStudentId(student.id);
  }

  onSubmit() {
    this.confirmed = false;
    this.studentClassAdmission = this.studentClassForm.value;
    this.studentClassService.createStudentAcademicLevel(this.studentClassAdmission)
    .subscribe((result: StudentClassAdmission) => {
      const resp = result;
      this.confirmed = true;
      this.studentClassForm.reset();
      this.dismiss();
    });
  }

  checkedChange(checked: boolean, jClass: ClassView) {
    if (checked) {
      this.jClassIds.push(jClass.id);
    } else {
      for (let i = 0; i < this.jClassIds.length; i++) {
        if ( this.jClassIds[i] === jClass.id) {
          this.jClassIds.splice(i, 1);
        }
     }
    }
    this.studentClassForm.patchValue({
      jClassIds: this.jClassIds,
    });
  }

  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      id: [null],
      studentId: ['', Validators.required],
      academicYearId: ['', Validators.required],
      academicLevelId: ['', Validators.required],
      jClassIds: [[], Validators.required],
    });
    this.onChanges();
  }

  onChanges() {
    this.studentClassForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      const programId = this.programId;
      this.academicYearId = this.currentAcademicYear.id;
      this.studentClassForm.patchValue({
        academicYearId: this.academicYearId,
      }, {emitEvent: false});
      if (academicLevelId && programId) {
        this.academicLevelId = academicLevelId;
        this.jClasses = [];
        this.loadJClassesByAcademicYear(programId, academicLevelId, this.academicYearId);
      }
    });
  }

  loadData() {
    this.isLoading = true;
    this.getCurrentAcademicYear();
    this.loadAcademicYears();
  }

  loadJClassesByAcademicYear(programId: number, academicLevelId: number, academicYearId: number) {
    if (!programId || !academicLevelId || !academicYearId) {
        this.modalService.openSnackBar('Program or Academic Level can not be empty', 'info');
        return;
    }
    this.isLoading = true;
    this.classService.loadJClassesByParams(programId, academicLevelId, academicYearId).subscribe((classViews: ClassView[]) => {
      this.jClasses = classViews;
      this.isLoading = false;
    });
  }
  loadAcademicYears() {
    this.academicYearService.getAcademicYears()
    .subscribe((academicYears: AcademicYear[]) => {
      this.isLoading = false;
      if (academicYears) {
        this.academicYears = academicYears;
      }
    });
  }

  pushJClasses(jClasses: ClassView[]) {
    const jClassesIds  = [];
    jClasses.forEach((jClass) => {
      jClassesIds.push(jClass.id);
    });
    return jClassesIds;
  }

  loadAvailableAcademicLevelsByStudentId(studentId: number) {
    this.academicLevels = [];
    this.academicLevelService.loadAvailableAcademicLevelsByStudentId(studentId)
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

  getCurrentAcademicYear() {
    this.academicYearService.getCurrentAcademicYear()
    .subscribe((academicYear: AcademicYear) => {
      this.isLoading = false;
      if (academicYear) {
        this.currentAcademicYear = academicYear;
      }
    });
  }
}
