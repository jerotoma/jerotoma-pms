import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { ClassView, AcademicYear } from 'app/models';
import {
  ClassService,
  AcademicYearService,
  ModalService,
 } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-classes-auto-generate',
  templateUrl: './classes-auto-generate.component.html',
  styleUrls: ['./classes-auto-generate.component.scss'],
})
export class ClassesAutoGenerateComponent implements OnInit {

  academicYearId: number;
  isLoading: boolean = false;
  classView: ClassView = null;
  classesAutoGenerateForm: FormGroup;
  academicYears: AcademicYear[];

  constructor(
    private modalService: ModalService,
    private academicYearService: AcademicYearService,
    private classService:  ClassService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<ClassesAutoGenerateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadAcademicYears();
  }

  dismiss(isClassesAutoGenerated: boolean) {
    this.ref.close({
      isClassesAutoGenerated: isClassesAutoGenerated,
    });
  }

  onSubmit() {
    this.classService.postAutoGenerateClasses(this.classesAutoGenerateForm.value)
        .subscribe((result: ClassView ) => {
          if (result) {
            this.modalService.openSnackBar('New classes have been auto generated', 'success');
            this.resetForm(true);
          }
        });
  }

  loadForm() {
    this.classesAutoGenerateForm = this.formBuilder.group({
      academicYearId: [null, Validators.required],
      action: ['auto', Validators.required],
    });
    this.onChanges();
  }

  onChanges(): void {
    this.classesAutoGenerateForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
        if (academicYearId) {

        }
      });
  }

  resetForm(isClassesAutoGenerated: boolean) {
    this.classesAutoGenerateForm.reset();
    this.dismiss(isClassesAutoGenerated);
  }

  loadAcademicYears() {
    this.academicYearService.getAcademicYears()
    .subscribe((academicYears: AcademicYear[]) => {
        if (academicYears) {
          this.academicYears = academicYears;
        }
      });
  }

}
