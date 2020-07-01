import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { AcademicLevel } from 'app/models';
import {
  AcademicLevelService,
  ModalService,
} from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-academic-level-create',
  templateUrl: `./academic-level-create.component.html`,
  styleUrls: [`./academic-level-create.component.scss`],
})
export class AcademicLevelCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;


  academicLevelForm: FormGroup;
  academicLevel: AcademicLevel;
  listDisplay: string = 'none';
  isSubmitting: boolean = false;

  constructor(
    private academicLevelService:  AcademicLevelService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<AcademicLevelCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === 'edit') {
        this.loadAcademicLevel();
    }
  }
  patchAcademicLevel() {
    this.academicLevelForm.patchValue({
      name: this.academicLevel.name,
      id: this.academicLevel.id,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.academicLevel = this.academicLevelForm.value;
        if (this.action === 'edit') {
            this.updateAcademicLevel();
        } else {
          this.academicLevelService.createAcademicLevel(this.academicLevel)
              .subscribe((academicLevel: AcademicLevel ) => {
                this.isSubmitting = false;
                  if (academicLevel) {
                    this.academicLevel = academicLevel;
                    this.modalService.openSnackBar('AcademicLevel ' + academicLevel.name + ' has been created', 'success');
                    this.dismiss();
                  }
              });
        }

  }
  updateAcademicLevel() {
    this.academicLevelService.updateAcademicLevel(this.academicLevel)
        .subscribe((academicLevel: AcademicLevel ) => {
          this.isSubmitting = false;
          if (academicLevel) {
            this.academicLevel = academicLevel;
            this.modalService.openSnackBar('AcademicLevel ' + academicLevel.name + ' has been updated', 'success');
            this.dismiss();
          }
      });
    }
  getDescriptionContent(description: string) {
   if (description) {
      this.academicLevelForm.patchValue({
        description: description,
      });
    }
  }

  loadForm() {
    this.academicLevelForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required ],
      code: ['', Validators.required ],
      description: [''],
    });
  }

  loadAcademicLevel() {
    this.academicLevelService.getAcademicLevel(parseInt(this.id, 10)).subscribe((academicLevel: AcademicLevel) => {
      if (academicLevel) {
        this.academicLevel = academicLevel;
        this.patchAcademicLevel();
      }
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
      userType: 'academicLevel',
    };
  }
}
