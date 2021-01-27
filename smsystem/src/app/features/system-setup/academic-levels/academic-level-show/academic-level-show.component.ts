import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

import {NbDialogService } from '@nebular/theme';
import { AcademicLevel, AcademicLevelPrerequisite } from 'app/models';
import {
  AcademicLevelService,
  ModalService,
} from 'app/services';
import { QueryParam, OPEN_CLOSE_ANIMATION } from 'app/utils';

@Component({
  selector: 'app-academic-level-show',
  animations: OPEN_CLOSE_ANIMATION,
  templateUrl: `./academic-level-show.component.html`,
  styleUrls: [`./academic-level-show.component.scss`],
})
export class AcademicLevelShowComponent implements OnInit {

  academiclevel: AcademicLevel;
  academiclevelId: number;
  academicLevelPrerequisites: AcademicLevelPrerequisite[];
  showRemoveAction: boolean = false;
  isLoadingAcademicLevel: boolean = false;
  isLoadingPrerequisite: boolean = false;

  constructor(
    private academiclevelService: AcademicLevelService,
    private dialogService: NbDialogService,
    private modalService: ModalService,
    private route: ActivatedRoute,
    private router: Router) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      window.console.log(params);
      this.academiclevelId = params['id'];
      this.loadAcademicLevel();
    });
  }
  loadAcademicLevel() {
    // throw new Error('Method not implemented.');
  }

}
