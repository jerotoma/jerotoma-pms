import { Component, OnInit} from '@angular/core';

import { NbDialogRef } from '@nebular/theme';
import { ScoreStandingService,  ModalService } from 'app/services';
import { ScoreStanding,  } from 'app/models';
import { APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-score-standing-delete',
  templateUrl: 'score-standing-delete.component.html',
  styleUrls: ['score-standing-delete.component.scss'],
})
export class ScoreStandingDeleteComponent implements OnInit {
  action: string = APP_ACTION_TYPE.delete;
  scoreStanding: ScoreStanding;
  confirmed: boolean = false;

  constructor(
    private scoreStandingService: ScoreStandingService,
    private modalService: ModalService,
    protected ref: NbDialogRef<ScoreStandingDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === APP_ACTION_TYPE.delete;
  }

  deleteScoreStanding() {
    if (this.confirmed) {
    this.scoreStandingService.deleteScoreStanding(this.scoreStanding.id)
      .subscribe((result: any ) => {
        if (result) {
         this.modalService.openSnackBar('ScoreStanding has been deleted', 'success');
        }
      });
    }
  }

  dismiss() {
    this.ref.close();
  }
  onConfirmed() {
    this.confirmed = true;
    this.deleteScoreStanding();
  }

}
