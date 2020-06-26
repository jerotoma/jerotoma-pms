import { Component, OnInit, Input } from '@angular/core';

import { Media } from 'app/models';

@Component({
  selector: 'app-media-pdfviewer',
  templateUrl: './media-pdf-viewer.component.html',
  styleUrls: ['./media-pdf-viewer.component.scss'],
})
export class MediaPDFViewerComponent implements OnInit {
  @Input('pdfFile') media: Media;


  constructor() { }

  ngOnInit(): void {
  }

}
