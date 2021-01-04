import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

import {NbDialogService } from '@nebular/theme';
import { Program, AcademicLevel, AcademicLevelPrerequisite } from 'app/models';
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
  academicLevelPrerequisites: AcademicLevelPrerequisite[];
  showRemoveAction: boolean = false;
  isLoadingAcademicLevel: boolean = false;
  isLoadingPrerequisite: boolean = false;

  constructor(
    private programService: ProgramService,
    private dialogService: NbDialogService,
    private modalService: ModalService,
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
  removeAcademicLevel(event: any, academicLevel: AcademicLevel) {
    event.preventDefault();
    event.stopPropagation();
    this.isLoadingAcademicLevel = true;
    this.programService.removeAcademicLevelFromProgram(this.programId, academicLevel.id)
      .subscribe((success: boolean) => {
          if (success) {
            this.modalService.openSnackBar('Academic Level has been removed', 'success');
            this.academicLevelPrerequisites = [];
            this.loadProgram();
            this.isLoadingAcademicLevel = false;
          }
      });
  }

  showAcademicLevelPrerequisite(event: any, academicLevel: AcademicLevel) {
    event.preventDefault();
    event.stopPropagation();
    this.academicLevelPrerequisites = academicLevel.prerequisites;
  }

  removeAcademicLevelPrerequisite(event: any, prerequisite: AcademicLevelPrerequisite) {
    event.preventDefault();
    event.stopPropagation();
    this.isLoadingPrerequisite = true;
    this.programService.removeProgramAcademicLevelPrerequisite(prerequisite)
      .subscribe((success: boolean) => {
          if (success) {
            this.modalService.openSnackBar('Academic Level has been removed', 'success');
            this.academicLevelPrerequisites = [];
            this.loadProgram();
            this.isLoadingPrerequisite = false;
          }
      });
  }

  removeDefaultEvent(event: any) {
    event.preventDefault();
    event.stopPropagation();
  }
}
