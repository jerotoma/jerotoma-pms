import { Component, OnInit } from '@angular/core';

import { MediaService, ModalService } from 'app/services';
import { Media, ResponseWrapper } from 'app/models';
import { QueryParam, USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-media-staffs',
  templateUrl: './media-staffs.component.html',
  styleUrls: ['./media-staffs.component.scss']
})
export class MediaStaffsComponent implements OnInit {

  mediaList: Media[] = [];

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: USER_TYPE.staff,
  };

  constructor(
    private mediaService: MediaService,
    private modalService: ModalService) { }

  ngOnInit(): void {
    this.loadMediaList();
  }

  loadMediaList() {
    this.mediaService.getMediaPaginated(this.param).subscribe((resp: ResponseWrapper) => {
      const data = resp.data;
      this.mediaList = data.mediaList;
      // console.log(this.mediaChunks);
    });
  }

}
