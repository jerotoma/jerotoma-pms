
import { Component, OnInit, Input } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { QueryParam, END_POINTS } from 'app/utils';

import { Media } from 'app/models';
@Component({
  selector: 'app-media-show',
  templateUrl: './media-show.component.html',
  styleUrls: ['./media-show.component.scss']
})
export class MediaShowComponent implements OnInit {

  @Input('media') media: Media;
  @Input('title') title: string;

  baseURL: string = END_POINTS.baseURL;

  constructor(protected ref: NbDialogRef<MediaShowComponent>) { }

  ngOnInit(): void {
  }

  dismiss(): void {
    this.media = null;
    this.ref.close({
      confirmed: true,
    });
  }
}
