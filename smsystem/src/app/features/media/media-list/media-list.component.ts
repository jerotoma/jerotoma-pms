
import { Component, OnInit, Input } from '@angular/core';
import { NbDialogService, NbMenuService } from '@nebular/theme';

import { UploadsComponent, DeleteModalComponent } from 'app/shared';
import { MediaShowComponent } from './../media-show/media-show.component';
import {
  GalleryService,
  GridLayout,
  PlainGalleryStrategy,
  PlainGalleryConfig,
  Image,
} from '@ks89/angular-modal-gallery';


import { MediaService, ModalService } from 'app/services';
import { Media, ResponseWrapper } from 'app/models';
import { QueryParam, END_POINTS, USER_TYPE } from 'app/utils';

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

  @Input('title') title: string = 'List of All Media';
  @Input('mediaList') mediaList: Media[] = [];
  @Input('isUserType') isUserType: boolean = false;

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
    userType: USER_TYPE.all,
  };
  media: Media;
  images: Image[] = [];
  searchTerm: string;

  plainGalleryGrid: PlainGalleryConfig = {
    strategy: PlainGalleryStrategy.GRID,
    layout: new GridLayout({ width: '24.59%', height: 'auto' }, { length: 4, wrap: true }),
  };

  constructor(
    private mediaService: MediaService,
    private modalService: ModalService,
    private dialogService: NbDialogService) { }

  ngOnInit(): void {
    if (!this.isUserType) {
      this.loadMediaList();
    } else {
      if (this.mediaList) {
        this.processImages();
      }
    }
  }

  findMatch(event: Event) {
    event.preventDefault();
    if (!this.searchTerm) {
      return;
    }
    const dataToSearch = {
      searchTerm: this.searchTerm,
    };
    this.mediaService.search(dataToSearch, this.param).subscribe((resp: ResponseWrapper) => {
      const data = resp.data;
      this.mediaList = data.dataList;
      this.processImages();
      // console.log(this.mediaChunks);
    });
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

  addNewMedia() {
    this.dialogService.open(UploadsComponent, {
      context: {
        title: 'Add New Media',
        action: 'create',
      },
    }).onClose.subscribe(data => {
      this.loadMediaList();
    });
  }

  changeToRowView() {
    this.activeView.isRowView = true;
    this.activeView.isView1 = false;
    this.activeView.isView2 = false;
  }

  showMediaView(event: Event, media: Media) {
    event.preventDefault();
    this.dialogService.open(MediaShowComponent, {
      context: {
        title: 'View ' + media.title,
        media: media,
      },
    }).onClose.subscribe(data => {

    });
  }

  deleteMedia(event: Event, media: Media) {
    event.preventDefault();
    this.dialogService.open(DeleteModalComponent, {
      context: {
        title: 'Delete Media',
        action: 'delete',
        id: media.id + '',
      },
    }).onClose.subscribe(data => {
      if (data.confirmed) {
        this.mediaService.deleteMedia(data.id).subscribe((result: boolean) => {
          if (result) {
            this.modalService.openSnackBar('Media has been deleted', 'success');
            this.loadMediaList();
          }
        });
      }
    });

  }

  loadMediaList() {
    this.mediaService.getMediaPaginated(this.param).subscribe((resp: ResponseWrapper) => {
      const data = resp.data;
      this.mediaList = data.mediaList;
      this.processImages();
      // console.log(this.mediaChunks);
    });
  }

  processImages() {
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
  }

}

