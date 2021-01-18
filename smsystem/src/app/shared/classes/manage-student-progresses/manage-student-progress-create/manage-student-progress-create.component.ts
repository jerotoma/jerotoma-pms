import { Component, OnInit, Input, ViewChild  } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray, FormControl } from '@angular/forms';
import {  ThemePalette } from '@angular/material/core';
import { MatSliderChange } from '@angular/material/slider';

import { NbDialogRef } from '@nebular/theme';
import { NbDialogService } from '@nebular/theme';

import {
  StudentAcademicLevelService,
  ClassService,
  AcademicYearService,
  ProgramService,
  StudentClassService,
  AcademicLevelService,
  UserService,
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
  classParam: TeacherClassParam
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];

  constructor(
    private modalService: ModalService,
    private programService: ProgramService,
    private studentClassProgressService: StudentClassService,
    private academicLevelService: AcademicLevelService,
    private academicYearService: AcademicYearService,
    private dialogService: NbDialogService,
    private formBuilder: FormBuilder,
    private classService: ClassService,
    protected ref: NbDialogRef<ManageStudentProgressCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
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
      this.studentProgressArray.push(this.patchStudentClassValue(studentClass.studentAcademicLevelId, studentClass.student.id, studentClass.statusId, studentClass.score));
    });
  }
  patchStudentClassValue(studentAcademicLevelId: number,studentId: number, statusId: number, score: number) {
    return this.formBuilder.group({
      studentAcademicLevelId: [studentAcademicLevelId, Validators.required],
      studentId: [studentId, Validators.required],
      statusId: [statusId, Validators.required],
      score: [score, Validators.required],
    });
  }

  onSliderValueChange(matSliderChange: MatSliderChange, studentClass: StudentClass) {
    console.log(this.manageStudentProgressForm);
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
}
