import { from } from 'rxjs';
import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { ResponseWrapper,  ClassView, AcademicYear, ClassAttendanceParam, ClassAttendance } from 'app/models';
import { ModalService, AcademicYearService, ClassService, ClassAttendanceService } from 'app/services';
import { APP_ACTION_TYPE, DateValidator } from 'app/utils';
import * as moment from 'moment';

@Component({
  selector: 'app-class-attendance-create',
  templateUrl: './class-attendance-create.component.html',
  styleUrls: ['./class-attendance-create.component.scss'],
})
export class ClassAttendenceCreateComponent implements OnInit {

  @Input() title: string;
  @Input() action: string = APP_ACTION_TYPE.create;
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;
  @Input() classAttendance: ClassAttendance;

  currentDate: Date = new Date();
  classAttendanceCreateForm: FormGroup;
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
    private classAttendanceService: ClassAttendanceService,
    protected ref: NbDialogRef<ClassAttendenceCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadAcademicYears();
    if (this.action === APP_ACTION_TYPE.edit) {

    }
  }

  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.classAttendanceParam = this.classAttendanceCreateForm.value;
    this.classAttendanceService.createClassAttendance(this.classAttendanceParam)
    .subscribe((classAttendance: ClassAttendance) => {
      this.dismiss();
      if (classAttendance) {
        window.console.log(classAttendance);
      }
    });
  }

  loadForm() {
    this.classAttendanceCreateForm = this.formBuilder.group({
      id: [null],
      academicYearId: [null, Validators.required],
      classId: ['', Validators.required ],
      attendanceDate: [null, DateValidator()],
    });
    this.onChanges();
  }
  onChanges() {
   this.classAttendanceCreateForm.get('classId').valueChanges.subscribe((classId: number) => {
      if (classId) {

      }
    });

    this.classAttendanceCreateForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
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
