import { from } from 'rxjs';
import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { ResponseWrapper,  ClassView, AcademicYear } from 'app/models';
import { ModalService, AcademicYearService, ClassService} from 'app/services';
import { APP_ACTION_TYPE, DateValidator } from 'app/utils';

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

  currentDate: Date = new Date();
  classAttendanceCreateForm: FormGroup;
  isSubmitting: boolean = false;
  isLoading: boolean = false;
  jClasses: ClassView[];
  academicYear: AcademicYear;
  academicYears: AcademicYear[];

  constructor(
    private academicYearService: AcademicYearService,
    private classService: ClassService,
    private formBuilder: FormBuilder,
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

  }

  loadForm() {
    this.classAttendanceCreateForm = this.formBuilder.group({
      id: [null],
      academicYearId: [null, Validators.required],
      classId: ['', Validators.required ],
      attendanceDateTime: [null, DateValidator('yyyy/MM/dd')],
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
