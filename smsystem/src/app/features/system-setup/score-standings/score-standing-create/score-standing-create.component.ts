import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { ScoreStanding, AcademicLevel, Department, Program } from 'app/models';
import {
  ScoreStandingService,
  AcademicLevelService,
  ProgramService,
  DepartmentService,
  ModalService,
} from 'app/services';
import { QueryParam, APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-score-standing-create',
  templateUrl: 'score-standing-create.component.html',
  styleUrls: ['score-standing-create.component.scss'],
})
export class ScoreStandingCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = APP_ACTION_TYPE.create;
  @Output() onCreationSuccess = new EventEmitter();
  @Input() scoreStanding:  ScoreStanding;

  scoreStandingForm: FormGroup;
  listDisplay: string = 'none';
  isSubmitting: boolean = false;
  selectedStandingColor: string = null;

  standingColors: string[] = [
    'info',
    'success',
    'warning',
    'primary',
    'secondary',
    'danger',
    'light',
    'dark',
  ];

  constructor(
    private scoreStandingService:  ScoreStandingService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<ScoreStandingCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.scoreStanding && this.action === APP_ACTION_TYPE.edit) {
      this.patchScoreStanding();
    }
  }

  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.scoreStanding = this.scoreStandingForm.value;
    if (this.action === APP_ACTION_TYPE.edit) {
      this.updateScoreStanding();
    } else {
      this.scoreStandingService.createScoreStanding(this.scoreStanding)
          .subscribe((scoreStanding: ScoreStanding ) => {
            this.isSubmitting = false;
              if (scoreStanding) {
                this.scoreStanding = scoreStanding;
                this.modalService.openSnackBar('Score Standing ' + scoreStanding.standing + ' has been created', 'success');
                this.dismiss();
              }
          });
    }

  }

  updateScoreStanding() {
    this.scoreStandingService.updateScoreStanding(this.scoreStanding)
        .subscribe((scoreStanding: ScoreStanding ) => {
          this.isSubmitting = false;
          if (scoreStanding) {
            this.scoreStanding = scoreStanding;
            this.modalService.openSnackBar('Score Standing ' + scoreStanding.standing + ' has been updated', 'success');
            this.dismiss();
          }
      });
  }

  loadForm() {
    this.scoreStandingForm = this.formBuilder.group({
      id: [null],
      standing: ['', Validators.required],
      minScore: [null, Validators.required],
      maxScore: [null, Validators.required],
      standingColor: ['', Validators.required],
    });
    this.onChanges();
  }

  onChanges() {
    this.scoreStandingForm.get('standingColor').valueChanges.subscribe((value: string) => {
      this.selectedStandingColor = value;
    });
  }

  patchScoreStanding() {
    this.scoreStandingForm.patchValue({
      id: this.scoreStanding.id,
      standing: this.scoreStanding.standing,
      minScore: this.scoreStanding.minScore,
      maxScore: this.scoreStanding.maxScore,
      standingColor: this.scoreStanding.standingColor,
    }, {emitEvent: false});
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
    };
  }
}
