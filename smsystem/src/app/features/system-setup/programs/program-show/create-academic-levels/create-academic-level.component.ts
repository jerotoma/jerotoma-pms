import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { Program, ProgramaAcademicLevelPrerequisiteParam, AcademicLevel } from 'app/models';
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

  isLoading: boolean;
  title: string;
  program: Program;
  academicLevels: AcademicLevel[];
  academicLevelPrerequisiteIds: number[] = [];
  academicLevelPrerequisiteParam: ProgramaAcademicLevelPrerequisiteParam;

  programId: number;
  isSubmitting: boolean = false;
  programAcademicLevelForm: FormGroup;
  constructor(
    private programService: ProgramService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    private academicLevelService: AcademicLevelService,
    protected ref: NbDialogRef<CreateAcademicLevelComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadAcademicLevels();
  }

  addNewAcademicLevel() {

  }

  checkedChange(checked: boolean, academicLevel: AcademicLevel) {
    if (checked) {
      this.academicLevelPrerequisiteIds.push(academicLevel.id);
    } else {
      for (let i = 0; i < this.academicLevelPrerequisiteIds.length; i++) {
        if ( this.academicLevelPrerequisiteIds[i] === academicLevel.id) {
          this.academicLevelPrerequisiteIds.splice(i, 1);
        }
     }
    }
    this.programAcademicLevelForm.patchValue({
      academicLevelPrerequisiteIds: this.academicLevelPrerequisiteIds,
    }, {emitEvent: false});

  }

  loadForm() {
    this.programAcademicLevelForm = this.formBuilder.group({
      programId: [this.program.id, Validators.required],
      academicLevelId: [null, Validators.required ],
      academicLevelPrerequisiteIds: [],
    });

    this.programAcademicLevelForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      this.loadAndFilterAcademicLevelsByAcademicLevelId(academicLevelId);
    });
  }

  onSubmit() {
    this.isSubmitting = true;
    this.academicLevelPrerequisiteParam = this.programAcademicLevelForm.value;
    this.programService.addAcademicLevelToProgram(this.academicLevelPrerequisiteParam)
        .subscribe((program: Program ) => {
          this.isSubmitting = false;
            if (program) {
              this.program = program;
              this.modalService.openSnackBar('Academic Level has been added to', 'success');
              this.ref.close({confirmed: true});
            }
        });
  }
  dismiss() {
    this.ref.close({confirmed: false});
  }

  loadAcademicLevels() {
    this.isLoading = true;
    this.academicLevelService.loadAcademicLevelList()
      .subscribe((academicLevels: AcademicLevel[]) => {
        this.isLoading = false;
        if (academicLevels) {
          this.academicLevels = academicLevels;
        }
      });
  }

  loadAndFilterAcademicLevelsByAcademicLevelId(academicLevelId: number) {
    this.isLoading = true;
    this.academicLevelService.loadAcademicLevelList()
      .subscribe((academicLevels: AcademicLevel[]) => {
        this.isLoading = false;
        if (academicLevels) {
          this.academicLevels = academicLevels.filter((academicLevel: AcademicLevel) => academicLevel.id != academicLevelId);
        }
      });
  }
}
