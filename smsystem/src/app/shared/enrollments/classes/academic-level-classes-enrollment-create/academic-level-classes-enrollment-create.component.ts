
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
  selector: 'app-academic-level-enrollment-create',
  styleUrls: ['./academic-level-classes-enrollment-create.component.scss'],
  templateUrl: './academic-level-classes-enrollment-create.component.html',
})
export class AcademicLevelClassesEnrollmentCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Input('student') student: Student = null;
  @Input() useSearch: boolean = false;
  @Output() onCreationSuccess = new EventEmitter();
  currentAcademicYearKey: string = APP_CONSTANTS.currentAcademicYear;

  userType: string = USER_TYPE.STUDENT;

  academicYearId: number;
  courseId: number;
  programId: number;
  academicLevelId: number;
  classIds: number[] = [];
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

  classes: ClassView[];
  academicYears: AcademicYear[];
  studentClassForm: FormGroup;
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  programs: Program[];

  constructor(
    private modalService: ModalService,
    private programService: ProgramService,
    private academicLevelService: AcademicLevelService,
    private academicYearService: AcademicYearService,
    private classService: ClassService,
    private studentClassService: StudentAcademicLevelService,
    private studentAcademicLevelService: StudentAcademicLevelService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<AcademicLevelClassesEnrollmentCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadData();
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
    if (checked) {
      this.classIds = [];
      this.classes.forEach((classView: ClassView) => {
        this.classIds.push(classView.id);
      });
      this.studentClassForm.patchValue({
        classIds: this.classIds,
      });
    } else {
      this.classIds = [];
      this.studentClassForm.patchValue({
        classIds: this.classIds,
      });
    }
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
    this.studentClassService.createStudentAcademicLevelClasses(this.studentClassAdmission)
    .subscribe((result: StudentClassAdmission) => {
      const resp = result;
      this.confirmed = true;
      this.modalService.openSnackBar('Student AcademicLevel Classes have been created', 'success');
      this.studentClassForm.reset();
      this.dismiss();
    });
  }

  checkedChange(checked: boolean, jClass: ClassView) {
    if (checked) {
      this.classIds.push(jClass.id);
    } else {
      for (let i = 0; i < this.classIds.length; i++) {
        if ( this.classIds[i] === jClass.id) {
          this.classIds.splice(i, 1);
        }
     }
    }
    this.studentClassForm.patchValue({
      classIds: this.classIds,
    });
  }

  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      id: [null],
      studentId: ['', Validators.required],
      academicYearId: ['', Validators.required],
      academicLevelId: ['', Validators.required],
      classIds: [[], Validators.required],
    });
    this.onChanges();
  }

  onChanges() {
    this.studentClassForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      const programId = this.programId;
      this.academicLevelId = academicLevelId;
      if (this.academicYearId && programId) {
        this.loadJClassesByAcademicYear(programId, academicLevelId, this.academicYearId);
      }
    });
    this.studentClassForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      const programId = this.programId;
      this.academicYearId = academicYearId;
      this.setSelectedAcademicYear(academicYearId);
      if (this.academicLevelId && programId) {
        this.loadJClassesByAcademicYear(programId, this.academicLevelId, academicYearId);
      }
    });
  }

  setSelectedAcademicYear(academicYearId: number) {
    this.academicYears.forEach(academicYear => {
      if (academicYear.id === academicYearId) {
        this.academicYear = academicYear;
      }
    });
  }

  loadData() {
    this.isLoading = true;
    if (this.student) {
      this.setSelectedUser(this.student);
    }
    this.loadAcademicYears();
  }

  loadJClassesByAcademicYear(programId: number, academicLevelId: number, academicYearId: number) {
    if (!programId || !academicLevelId || !academicYearId) {
        this.modalService.openSnackBar('Program or Academic Level can not be empty', 'info');
        return;
    }
    this.classes = [];
    this.isLoading = true;
    this.classService.loadJClassesByParams(programId, academicLevelId, academicYearId).subscribe((classViews: ClassView[]) => {
      this.classes = classViews;
      if (classViews && classViews.length === 0) {
        this.modalService.openSnackBar('No classes for the selected school year', 'info');
      }
      this.isLoading = false;
    });
  }
  loadAcademicYears() {
    this.isLoading = true;
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
    this.isLoading = true;
    this.academicLevels = [];
    this.academicLevelService.loadStudentRegisteredAcademicLevels(studentId)
    .subscribe((academicLevels: AcademicLevel[] ) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
      }
      this.isLoading = false;
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
}
