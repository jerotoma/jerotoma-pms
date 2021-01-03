
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
  AcademicLevelPrerequisite,
  Program,
} from 'app/models';

@Component({
  selector: 'app-student-academic-level-classes-enrollment-create',
  styleUrls: ['./student-academic-level-enrollment-create.component.scss'],
  templateUrl: './student-academic-level-enrollment-create.component.html',
})
export class StudentAcademicLevelEnrollmentCreateComponent implements OnInit {
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
  isLoading: boolean = false;
  currentAcademicYear: AcademicYear = null;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType:  this.userType,
  };

  confirmed: boolean = false;
  student: Student;
  studentAcademicLevelForm: FormGroup;
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  academicLevelPrerequisites: AcademicLevelPrerequisite[];
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
    protected ref: NbDialogRef<StudentAcademicLevelEnrollmentCreateComponent>) {}

  ngOnInit() {
    this.loadData();
    this.loadForm();
  }

  dismiss() {
    this.ref.close({
      confirmed: this.confirmed,
    });
  }
  setSelectedUser(student: Student) {
    this.studentAcademicLevelForm.patchValue({
      studentId: student.id,
    });
    this.programId = student.programId;
    this.student = student;
    this.loadAvailableAcademicLevelsByStudentId(student.id);
  }

  onSubmit() {
    this.confirmed = false;
    this.studentAcademicLevelService.createStudentAcademicLevel(this.studentAcademicLevelForm.value)
    .subscribe((result: StudentAcademicLevel) => {
      this.confirmed = true;
      this.studentAcademicLevelForm.reset();
      this.dismiss();
    });
  }

  loadForm() {
    this.studentAcademicLevelForm = this.formBuilder.group({
      id: [null],
      studentId: ['', Validators.required],
      academicYearId: ['', Validators.required],
      academicLevelId: ['', Validators.required],
      isCurrentAcademicLevel: [false],
    });
    this.onChanges();
  }

  onChanges() {
    this.studentAcademicLevelForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      this.academicLevelPrerequisites = [];
      for (let i = 0; i < this.academicLevels.length; i++) {
        if (this.academicLevels[i].id === academicLevelId) {
          this.academicLevelPrerequisites = this.academicLevels[i].prerequisites;
        }
      }
      this.academicYearId = this.currentAcademicYear.id;
      this.studentAcademicLevelForm.patchValue({
        academicYearId: this.academicYearId,
        studentId: this.student.id,
      }, {emitEvent: false});
    });
  }

  loadData() {
    this.isLoading = true;
    this.getCurrentAcademicYear();
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

  getCurrentAcademicYear() {
    this.academicYearService.getCurrentAcademicYear()
    .subscribe((academicYear: AcademicYear) => {
      this.isLoading = false;
      if (academicYear) {
        this.currentAcademicYear = academicYear;
      }
    });
  }

  removeAcademicLevel(event: any, academicLevel: AcademicLevel, isRemoveLevel: boolean) {
    event.preventDefault();
    event.stopPropagation();
  }
}
