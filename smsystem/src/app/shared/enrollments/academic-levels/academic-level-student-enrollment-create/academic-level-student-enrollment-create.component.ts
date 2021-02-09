
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import {
  StudentAcademicLevelService,
  AcademicYearService,
  ProgramService,
  AcademicLevelService,
  ModalService,
 } from 'app/services';
import { USER_TYPE } from 'app/utils';
import {
  AcademicLevelPrerequisite,
  Student,
  AcademicYear,
  StudentAcademicLevel,
  AcademicLevel,
  Program,
} from 'app/models';

@Component({
  selector: 'app-academic-level-student-enrollment-create',
  styleUrls: ['./academic-level-student-enrollment-create.component.scss'],
  templateUrl: './academic-level-student-enrollment-create.component.html',
})
export class AcademicLevelStudentEnrollmentCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Input() student: Student = null;
  @Input() useSearch: boolean = false;
  @Output() onCreationSuccess = new EventEmitter();


  userType: string = USER_TYPE.STUDENT;
  academicYearId: number;
  programId: number;
  academicLevelId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  academicYears: AcademicYear[];

  studentAcademicLevelForm: FormGroup;
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  academicLevelPrerequisites: AcademicLevelPrerequisite[];
  programs: Program[];

  constructor(
    private modalService: ModalService,
    private programService: ProgramService,
    private academicLevelService: AcademicLevelService,
    private academicYearService: AcademicYearService,
    private studentAcademicLevelService: StudentAcademicLevelService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<AcademicLevelStudentEnrollmentCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadData();
  }

  dismiss() {
    this.ref.close({
      confirmed: this.confirmed,
    });
  }

  onSubmit() {
    this.confirmed = false;
    this.studentAcademicLevelService.createStudentAcademicLevel(this.studentAcademicLevelForm.value)
    .subscribe((result: StudentAcademicLevel) => {
      this.modalService.openSnackBar('Student AcademicLevel have been created', 'success');
      this.confirmed = true;
      this.studentAcademicLevelForm.reset();
      this.dismiss();
    });
  }

  loadForm() {
    this.studentAcademicLevelForm = this.formBuilder.group({
      id: [null],
      studentId: [null, Validators.required],
      academicYearId: [null, Validators.required],
      academicLevelId: [null, Validators.required],
      isCurrentStudentAcademicLevel: [false, Validators.required],
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
    });
    if (this.student) {
      this.studentAcademicLevelForm.patchValue({
        studentId: this.student.id,
      });
    }
  }

  loadData() {
    this.isLoading = true;
    this.loadAcademicYears();
    if (this.student) {
      this.loadAvailableAcademicLevelsByStudentId(this.student.id);
    }
    this.loadPrograms();
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

  loadAvailableAcademicLevelsByStudentId(studentId: number) {
    this.academicLevels = [];
    this.academicLevelService.loadStudentUnregisteredAcademicLevels(studentId)
    .subscribe((academicLevels: AcademicLevel[] ) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
      }
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

  removeAcademicLevel(event: any, academicLevel: AcademicLevel, isRemoveLevel: boolean) {
    event.preventDefault();
    event.stopPropagation();
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
