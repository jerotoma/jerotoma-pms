import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

import {NbDialogService } from '@nebular/theme';
import { AcademicLevel, Stream } from 'app/models';
import { AddStreamComponent } from './academic-level-streams/add-stream.component';
import {
  AcademicLevelService,
  ModalService,
} from 'app/services';
import { OPEN_CLOSE_ANIMATION, APP_ACTION_TYPE } from 'app/utils';

import { StreamCreateComponent } from 'app/shared';

@Component({
  selector: 'app-academic-level-show',
  animations: OPEN_CLOSE_ANIMATION,
  templateUrl: `./academic-level-show.component.html`,
  styleUrls: [`./academic-level-show.component.scss`],
})
export class AcademicLevelShowComponent implements OnInit {

  academiclevel: AcademicLevel;
  academiclevelId: number;
  streams: Stream[];
  showRemoveAction: boolean = false;
  isLoading: boolean = false;

  constructor(
    private academicLevelService: AcademicLevelService,
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
    this.isLoading = true;
    this.academicLevelService.getAcademicLevel(this.academiclevelId)
      .subscribe((academiclevel: AcademicLevel) => {
        this.isLoading = false;
        this.academiclevel = academiclevel;
        this.streams = academiclevel.streams;
      });
  }

  preventDefault(event: any) {
    event.preventDefault();
  }

  removeStream(event: any, stream: Stream) {
    event.preventDefault();
  }

  addNewStream() {
    this.dialogService.open(AddStreamComponent, {
      context: {
        title: 'Add New Stream',
        academicLevel: this.academiclevel,
      },
    }).onClose.subscribe(data => {
      if (data.confirmed) {
        this.loadAcademicLevel();
      }
    });
  }
}
