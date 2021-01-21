import { Component, OnInit, Input, ViewChild, ElementRef  } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray, FormControl } from '@angular/forms';
import {  ThemePalette } from '@angular/material/core';
import { MatSliderChange } from '@angular/material/slider';

import { NbDialogRef } from '@nebular/theme';
import { NbDialogService } from '@nebular/theme';

import {
  StudentAcademicLevelService,
  ClassService,
  StatusService,
  AcademicYearService,
  ProgramService,
  StudentClassService,
  AcademicLevelService,
  ScoreStandingService,
  ModalService,
 } from 'app/services';
import { QueryParam, USER_TYPE, APP_ACTION_TYPE } from 'app/utils';
import {
  ShowMessage,
  ClassView,
  StudentClassAdmission,
  Student,
  Teacher,
  StudentClass,
  AcademicYear,
  StudentAcademicLevel,
  ScoreStanding,
  AcademicLevel,
  TeacherClassParam,
  Program,
  ResponseWrapper,
} from 'app/models';

@Component({
  selector: 'app-manage-student-progress-create',
  templateUrl: './manage-student-progress-create.component.html',
  styleUrls: ['./manage-student-progress-create.component.scss'],
})
export class ManageStudentProgressCreateComponent implements OnInit {

  @Input() title: string;
  @Input() action: string =  APP_ACTION_TYPE.create;
  @Input('classView') classView: ClassView;

  manageStudentProgressForm: FormGroup;
  isLoading: boolean = false;
  confirmed: boolean = false;
  academicYearId: number;
  programId: number;
  academicLevelId: number;
  teacherId: number;
  sliderColor: ThemePalette = 'warn';
  sliderMin: number = 0.01;
  sliderMax: number = 100;
  sliderStep: number = 1;

  studentClasses: StudentClass[];
  scoreStandings: ScoreStanding[];
  classParam: TeacherClassParam
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];

  constructor(
    private modalService: ModalService,
    private elementRef : ElementRef,
    private programService: ProgramService,
    private studentClassProgressService: StudentClassService,
    private scoreStandingService: ScoreStandingService,
    private formBuilder: FormBuilder,
    private statusService: StatusService,
    protected ref: NbDialogRef<ManageStudentProgressCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadScoreStandings();
    this.loadStudentClassesProgressByClassAttendanceId(this.classView.id);
  }

  dismiss() {
    this.ref.close({
      confirmed: this.confirmed,
    });
  }

  onSubmit() {
    console.log(this.manageStudentProgressForm.value);
    this.studentClassProgressService.createStudentClassProgress(this.manageStudentProgressForm.value).subscribe((resp: ResponseWrapper) => {
      if (resp.success) {
        this.modalService.openSnackBar('Student Progress has been updated', 'info');
        this.dismiss();
      }
    });
  }

  loadForm() {
    this.manageStudentProgressForm = this.formBuilder.group({
      classId: [null, Validators.required],
      academicYearId: [null, Validators.required],
      academicLevelId: [null, Validators.required],
      programId: [null, Validators.required],
      studentProgressArray: this.formBuilder.array([]),
    });
    this.manageStudentProgressForm.patchValue({
      classId: this.classView.id,
      academicYearId: this.classView.academicYear.id,
      academicLevelId: this.classView.course.academicLevel.id,
      programId: this.classView.course.program.id,
    });
    this.onChanges();
  }

  patchStudentClasses(studentClasses: StudentClass[]) {
    this.studentClasses = studentClasses;
    this.studentClasses.forEach((studentClass: StudentClass, index: number) => {
      this.studentProgressArray.push(this.patchStudentClassValue(studentClass));
    });
  }
  patchStudentClassValue(studentClass: StudentClass) {
    return this.formBuilder.group({
      studentAcademicLevelId: [studentClass.studentAcademicLevelId, Validators.required],
      studentId: [studentClass.student.id, Validators.required],
      statusId: [studentClass.statusId, Validators.required],
      score: [studentClass.score, Validators.required],
      scoreStandingId: [studentClass?.scoreStanding?.id, Validators.required],
    });
  }

  onSliderValueChange(matSliderChange: MatSliderChange, studentClass: StudentClass) {
    studentClass.score = matSliderChange.value;
    for (let i = 0; i < this.studentProgressArray.controls.length; i++) {
      if (this.studentProgressArray.controls[i].value.studentId === studentClass.student.id) {
        this.studentProgressArray.controls[i].patchValue({
          studentAcademicLevelId: studentClass.studentAcademicLevelId,
          studentId: studentClass.student.id,
          statusId: studentClass.statusId,
          score: studentClass.score,
        });
      }
    }
    const domElement = this.elementRef.nativeElement.querySelector('#student-class-current-slider-value-' + studentClass.student.id);
    domElement.innerHTML = studentClass.score;
  }

  loadScoreStandings() {
    this.scoreStandingService.getScoreStandings().subscribe((scoreStandings: ScoreStanding[]) => {
      this.scoreStandings = scoreStandings;
    });
  }
  loadStudentClassesProgressByClassAttendanceId(classId: number) {
    this.studentClassProgressService.loadStudentsClassProgressByClassId(classId).subscribe((studentClasses: StudentClass[]) => {
      this.patchStudentClasses(studentClasses);
    });
  }

  get studentProgressArray(): FormArray {
    return this.manageStudentProgressForm.get('studentProgressArray') as FormArray;
  }

  onChanges() {
    this.manageStudentProgressForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      this.academicYearId = academicYearId;
      if (this.programId && this.academicLevelId) {

      }
     });

    this.manageStudentProgressForm.get('programId').valueChanges.subscribe((programId: number) => {
     if (this.programId != programId)  {

     }
    });

    this.manageStudentProgressForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      if (this.academicLevelId != academicLevelId)  {

      }
    });
  }

  getStatusClass(statusName: string): string {
    return this.statusService.getCompletionStatusClass(statusName);
  }
}
