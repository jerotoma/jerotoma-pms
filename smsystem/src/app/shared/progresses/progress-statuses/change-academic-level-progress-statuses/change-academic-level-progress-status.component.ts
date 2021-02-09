
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {  ThemePalette } from '@angular/material/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import {
  StudentAcademicLevelService,
  ClassService,
  ModalService,
  AcademicYearService,
  AcademicLevelService,
  StudentProgressService,
  StatusService,
 } from 'app/services';

import {
  Student,
  AcademicLevelProgress,
  ProgressStatus,
  StudentAcademicLevelsProgress,
} from 'app/models';

@Component({
  selector: 'app-change-academic-level-progress-status',
  templateUrl: './change-academic-level-progress-status.component.html',
  styleUrls: ['./change-academic-level-progress-status.component.scss'],
})
export class ChangeAcademicLevelProgressStatusComponent implements OnInit {

  @Input() academicLevelProgress: AcademicLevelProgress;
  @Input() student: Student;
  @Input() title: string = '';

  changeProgressStatusForm: FormGroup;
  confirmed: boolean = false;
  progressStatuses: ProgressStatus[];
  studentAcademicLevelsProgress: StudentAcademicLevelsProgress;

  constructor(
    private studentProgressService: StudentProgressService,
    private statusService: StatusService,
    private modalService: ModalService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<ChangeAcademicLevelProgressStatusComponent>) {}

  ngOnInit(): void {
    this.loadForm();
    this.loadProgressStatuses();
  }

  dismiss(studentAcademicLevelsProgress?: StudentAcademicLevelsProgress) {
    this.ref.close({
      confirmed: this.confirmed,
      studentAcademicLevelsProgress: studentAcademicLevelsProgress,
    });
  }

  loadForm() {
    this.changeProgressStatusForm = this.formBuilder.group({
      academicYearId: [null, Validators.required],
      academicLevelId: [null, Validators.required],
      studentId: [null, Validators.required],
      progressStatusId: [null, Validators.required],
    });

    this.changeProgressStatusForm.patchValue({
      academicYearId: this.academicLevelProgress.academicYear.id,
      academicLevelId:  this.academicLevelProgress.academicLevel.id,
      studentId: this.student.id,
    });
  }

  onSubmit() {
    this.studentProgressService.updateStudentAcademicLevelsProgress(this.changeProgressStatusForm.value)
    .subscribe((studentAcademicLevelsProgress: StudentAcademicLevelsProgress) => {
      this.studentAcademicLevelsProgress = studentAcademicLevelsProgress;
      this.confirmed = true;
      this.dismiss(studentAcademicLevelsProgress);

    });
  }

  loadProgressStatuses() {
    this.statusService.loadProgressStatuses().subscribe((progressStatuses: ProgressStatus[]) => {
      this.progressStatuses = progressStatuses;
      this.changeProgressStatusForm.patchValue({
        progressStatusId: this.academicLevelProgress.completionStatusId,
      });
    });
  }

}
