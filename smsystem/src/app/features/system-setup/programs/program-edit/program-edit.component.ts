import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { NbDialogRef } from '@nebular/theme';
import { Program, AcademicLevel } from 'app/models';
import {
  ProgramService,
  ModalService,
} from 'app/services';
import { QueryParam, FRONTEND_ENDPOINTS } from 'app/utils';

@Component({
  selector: 'app-program-edit',
  templateUrl: `./program-edit.component.html`,
  styleUrls: [`./program-edit.component.scss`],
})
export class ProgramEditComponent implements OnInit {
  @Output() onCreationSuccess = new EventEmitter();
  @Input('program')  program: Program;
  programForm: FormGroup;
  isAddMore: boolean = false;

  academicLevels: AcademicLevel[];
  academicLevelIDs: number[] = [];
  listDisplay: string = 'none';
  isSubmitting: boolean = false;
  isLoading: boolean = false;

  constructor(
    private programService:  ProgramService,
     private formBuilder: FormBuilder,
    private modalService: ModalService,
    private router: Router,
    protected ref: NbDialogRef<ProgramEditComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.patchProgram();
  }
  patchProgram() {
    this.programForm.patchValue({
      name: this.program.name,
      code: this.program.code,
      description: this.program.description,
      id: this.program.id,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.program = this.programForm.value;
    this.programService.updateProgram(this.program)
      .subscribe((program: Program ) => {
        this.isSubmitting = false;
        if (program) {
          this.program = program;
          this.modalService.openSnackBar('Program ' + program.name + ' has been updated', 'success');
          this.dismiss();
          this.router.navigate([FRONTEND_ENDPOINTS.systemSetupPrograms.path + '/' + program.id]);
        }
    });
  }

  loadForm() {
    this.programForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required ],
      code: ['', Validators.required ],
      description: [''],
    });
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'program',
    };
  }
}
