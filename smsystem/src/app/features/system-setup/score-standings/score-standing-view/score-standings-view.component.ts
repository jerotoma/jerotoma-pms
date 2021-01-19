import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { ScoreStandingCreateComponent } from '../score-standing-create/score-standing-create.component';
import { ScoreStandingDeleteComponent } from '../score-standing-delete/score-standing-delete.component';
import { ScoreStanding, ResponseWrapper } from 'app/models';
import { ScoreStandingService } from 'app/services';
import { APP_ACTION_TYPE } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-courses-view',
  styleUrls: ['score-standings-view.component.scss'],
  templateUrl: 'score-standings-view.component.html',
})
export class ScoreStandingsViewComponent implements OnInit {

  title: string = 'List of Score Standings';
  course: ScoreStanding;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'standing', 'minScore', 'maxScore', 'createdOn', 'action'];
  dataSource: MatTableDataSource<ScoreStanding> = new MatTableDataSource<ScoreStanding>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private courseService: ScoreStandingService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadScoreStandings();
  }

  open() {
    this.dialogService.open(ScoreStandingCreateComponent, {
      context: {
        title: 'Add New Score Standing',
        action: APP_ACTION_TYPE.create,
      },
    }).onClose.subscribe(_data => {
      this.loadScoreStandings();
    });
  }

  loadScoreStandings() {
    this.isLoading = true;
    this.courseService.getScoreStandings()
      .subscribe((scoreStandings: ScoreStanding[]) => {
        this.isLoading = false;
        if (scoreStandings) {
          this.dataSource.data = scoreStandings;
        }
      });
  }
  edit(scoreStanding: ScoreStanding) {
    this.dialogService.open(ScoreStandingCreateComponent, {
      context: {
        title: 'Edit Score Standing',
        action: APP_ACTION_TYPE.edit,
        scoreStanding: scoreStanding,
      },
    }).onClose.subscribe(_data => {
      this.loadScoreStandings();
    });
  }
  delete(scoreStanding: ScoreStanding) {
    this.dialogService.open(ScoreStandingDeleteComponent, {
      context: {
        action: APP_ACTION_TYPE.delete,
        scoreStanding: scoreStanding,
      },
    }).onClose.subscribe(_data => {
      this.loadScoreStandings();
    });
  }
}
