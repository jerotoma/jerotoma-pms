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
  selector: 'app-program-show',
  animations: OPEN_CLOSE_ANIMATION,
  templateUrl: `./program-show.component.html`,
  styleUrls: [`./program-show.component.scss`],
})
export class ProgramShowComponent implements OnInit {
  @Input('program')  program: Program;
  constructor(protected ref: NbDialogRef<ProgramShowComponent>) {}

  ngOnInit() { }

  dismiss() {
    this.ref.close();
  }

  removeAcademicLevel(event: any, academicLevel: AcademicLevel, isRemoveLevel: boolean) {
    event.preventDefault();
    event.stopPropagation();
  }
}
