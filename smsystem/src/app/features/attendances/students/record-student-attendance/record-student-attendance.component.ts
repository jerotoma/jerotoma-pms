import { from } from 'rxjs';
import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { ResponseWrapper,  ClassView, AcademicYear, ClassAttendanceParam, ClassAttendance } from 'app/models';
import { ModalService, AcademicYearService, ClassService, StudentAttendanceService } from 'app/services';
import { APP_ACTION_TYPE, DateValidator } from 'app/utils';

@Component({
  selector: 'app-record-student-attendance',
  templateUrl: './record-student-attendance.component.html',
  styleUrls: ['./record-student-attendance.component.scss'],
})
export class RecordStudentAttendenceComponent implements OnInit {

  @Input() title: string;
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;
  @Input() classAttendance: ClassAttendance;

  currentDate: Date = new Date();
  recordAttendanceForm: FormGroup;
  isSubmitting: boolean = false;
  isLoading: boolean = false;
  jClasses: ClassView[];
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  classAttendanceParam: ClassAttendanceParam;

  constructor(
    private academicYearService: AcademicYearService,
    private classService: ClassService,
    private formBuilder: FormBuilder,
    private studentAttendanceService: StudentAttendanceService) {}

  ngOnInit() {
    this.loadForm();
    this.loadAcademicYears();
  }

  onSubmit() {
    this.classAttendanceParam = this.recordAttendanceForm.value;
    this.studentAttendanceService.createClassAttendance(this.classAttendanceParam)
    .subscribe((classAttendance: ClassAttendance) => {
      if (classAttendance) {
        window.console.log(classAttendance);
      }
    });
  }

  loadForm() {
    this.recordAttendanceForm = this.formBuilder.group({
      id: [null],
      academicYearId: [null, Validators.required],
      classId: ['', Validators.required ],
      attendanceDate: [null, DateValidator('yyyy/MM/dd')],
    });
    this.onChanges();
  }
  onChanges() {
   this.recordAttendanceForm.get('classId').valueChanges.subscribe((classId: number) => {
      if (classId) {

      }
    });

    this.recordAttendanceForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      if (academicYearId != null) {
        this.loadJClassesByAcademicYear(academicYearId);
      }
    });
  }

  loadAcademicYears() {
    this.academicYearService.getAcademicYears()
    .subscribe((academicYears: AcademicYear[]) => {
        if (academicYears) {
          this.academicYears = academicYears;
        }
      });
  }

  loadJClassesByAcademicYear(academicYearId: number) {
    this.isLoading = true;
    this.classService.loadJClassesByAcademicYear(academicYearId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }
}
