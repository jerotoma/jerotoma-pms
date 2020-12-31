import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { Program, AcademicLevel } from 'app/models';
import {
  ProgramService,
  AcademicLevelService,
  ModalService,
} from 'app/services';
import { QueryParam, OPEN_CLOSE_ANIMATION } from 'app/utils';

@Component({
  selector: 'app-create-academic-level',
  animations: OPEN_CLOSE_ANIMATION,
  templateUrl: `./create-academic-level.component.html`,
  styleUrls: [`./create-academic-level.component.scss`],
})
export class CreateAcademicLevelComponent implements OnInit {

  title: string;
  program: Program;
  programId: number;
  isSubmitting: boolean = false;
  programAcademicLevelForm: FormGroup;
  constructor(
    private programService: ProgramService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<CreateAcademicLevelComponent>) {}

  ngOnInit() {
    this.loadForm();
  }

  addNewAcademicLevel() {

  }

  removeAcademicLevel(event: any, academicLevel: AcademicLevel, isRemoveLevel: boolean) {
    event.preventDefault();
    event.stopPropagation();
  }

  loadForm() {
    this.programAcademicLevelForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required ],
      code: ['', Validators.required ],
      description: [''],
    });
  }

  onSubmit() {
    this.isSubmitting = true;
    this.program = this.programAcademicLevelForm.value;
    this.programService.createProgram(this.program)
        .subscribe((program: Program ) => {
          this.isSubmitting = false;
            if (program) {
              this.program = program;
              this.modalService.openSnackBar('Program ' + program.name + ' has been created', 'success');
            }
        });
  }

  dismiss() {
    this.ref.close();
  }
}
