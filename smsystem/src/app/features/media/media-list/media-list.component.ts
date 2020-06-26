import { Component, OnInit } from '@angular/core';

import {
  GalleryService,
  GridLayout,
  PlainGalleryStrategy,
  PlainGalleryConfig,
  Image,
} from '@ks89/angular-modal-gallery';


import { MediaService } from 'app/services';
import { Media, ResponseWrapper } from 'app/models';
import { QueryParam, END_POINTS } from 'app/utils';

export interface ActiveView {
  gridView: number;
  isRowView: boolean;
  isView1: boolean;
  isView2: boolean;
}

@Component({
  selector: 'app-media-list',
  templateUrl: './media-list.component.html',
  styleUrls: ['./media-list.component.scss'],
})
export class MediaListComponent implements OnInit {

  baseURL: string = END_POINTS.baseURL;
  public activeView: ActiveView = {
    gridView: 25,
    isRowView: false,
    isView1: false,
    isView2: false,
  };

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };
  media: Media;
  mediaList: Media[] = [];
  mediaChunks: Array<Media[]> = [];
  images: Image[] = [];
  searchTerm: string;

  plainGalleryGrid: PlainGalleryConfig = {
    strategy: PlainGalleryStrategy.GRID,
    layout: new GridLayout({ width: '180px', height: '180px' }, { length: 3, wrap: true }),
  };

  constructor(private mediaService: MediaService) { }

  ngOnInit(): void {
    this.loadMediaList();
  }

  findMatch(event: any) {

  }

  changeViewGrid(num: number) {
    switch (num) {
      case 1:
         this.activeView.isRowView = false;
         this.activeView.gridView = 100;
         this.activeView.isView1 = true;
         this.activeView.isView2 = false;
         break;
      case 2:
          this.activeView.isRowView = false;
          this.activeView.gridView = 50;
          this.activeView.isView1 = false;
          this.activeView.isView2 = true;
         break;
      default:
           this.activeView.isRowView = false;
           this.activeView.gridView = 25;
           this.activeView.isView1 = false;
           this.activeView.isView2 = false;
         break;
    }
  }

  showUploadMediaView() {

  }

  changeToRowView() {
    this.activeView.isRowView = true;
    this.activeView.isView1 = false;
    this.activeView.isView2 = false;
  }

  showMediaView(media: Media) {
    this.media = media;
  }

  deleteMedia(file: Media) {

  }

  loadMediaList() {
    this.mediaService.getMediaPaginated(this.param).subscribe((resp: ResponseWrapper) => {
      const data = resp.data;
      this.mediaList = data.mediaList;
      this.mediaChunks = data.mediaChunks;
      this.images = [];
      this.mediaList.forEach((media, index) => {
        this.images.push(new Image(media.id, {
          img: this.baseURL + media.src,
          title: media.title,
          alt: media.title,
          extUrl: this.baseURL,
          description: media.description,
        }, {
          img: this.baseURL + media.src,
          alt: media.title,
          title: media.title,
          description: media.description,
          ariaLabel: media.title,
        }));
      });
      // console.log(this.mediaChunks);
    });
  }
}

