import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

import {NbDialogService } from '@nebular/theme';
import { Program, AcademicLevel } from 'app/models';
import {
  ProgramService,
  AcademicLevelService,
  ModalService,
} from 'app/services';
import { QueryParam, OPEN_CLOSE_ANIMATION } from 'app/utils';

import { CreateAcademicLevelComponent } from './create-academic-levels/create-academic-level.component';

@Component({
  selector: 'app-program-show',
  animations: OPEN_CLOSE_ANIMATION,
  templateUrl: `./program-show.component.html`,
  styleUrls: [`./program-show.component.scss`],
})
export class ProgramShowComponent implements OnInit {

  program: Program;
  programId: number;
  constructor(
    private programService: ProgramService,
    private dialogService: NbDialogService,
    private route: ActivatedRoute,
    private router: Router,) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      this.programId = params['id'];
      this.loadProgram();
    });
  }


  addNewAcademicLevel() {
    this.dialogService.open(CreateAcademicLevelComponent, {
      context: {
        title: 'Add New Academic Level',
        program: this.program,
      },
    }).onClose.subscribe(data => {
          if (data.confirmed) {
            this.loadProgram();
          }
    });
  }

  loadProgram() {
    this.programService.getProgram(this.programId).subscribe((program: Program) => {
      this.program = program;
    })
  }
  removeAcademicLevel(event: any, academicLevel: AcademicLevel, isRemoveLevel: boolean) {
    event.preventDefault();
    event.stopPropagation();
  }
}
