
import { Component, Input, EventEmitter, OnInit } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { FileUploader, FileItem } from 'ng2-file-upload';
import { HttpEvent, HttpEventType } from '@angular/common/http';

import {  ModalService, UploadService } from 'app/services';
import { User } from 'app/models';
import { USER_TYPE, API_END_POINTS } from 'app/utils';

@Component({
  selector: 'app-upload-avatar-dialog',
  templateUrl: 'upload-avatar-dialog.component.html',
  styleUrls: ['./upload-avatar-dialog.component.scss'],
})
export class UploadAvatarDialogComponent implements OnInit {
  @Input('user') user: User;
  @Input('onSuccess') onSuccess: EventEmitter<boolean> = new EventEmitter<boolean>();

  public uploader: FileUploader;
  mediaURL: any = null;
  uploadDir: string = 'users';
  formData: FormData;
  userAvatar: File;
  progress: number = 0;

  constructor(
    private uploadService: UploadService,
    private modalService: ModalService,
    protected ref: NbDialogRef<UploadAvatarDialogComponent>) {}

  ngOnInit(): void {
    this.initUploader();
  }

  dismiss(confirmed: boolean): void {
    this.userAvatar = null;
    this.uploader.clearQueue();
    this.uploader.destroy();
    this.ref.close({
      confirmed: confirmed,
    });
  }

  public onFileSelected(event: EventEmitter<File[]>) {
    const file: File = event[0];
    this.userAvatar = file;
    this.readFile(file);
  }

  onSubmit() {
    if (this.userAvatar) {
      const formData = new FormData();
      formData.append('media_file', this.userAvatar);
      this.uploadService.uploadProfileImageTrackProgress(this.user.userId, formData).subscribe((event: HttpEvent<any>) => {
        switch (event.type) {
          case HttpEventType.Sent:
            break;
          case HttpEventType.ResponseHeader:
            break;
          case HttpEventType.UploadProgress:
            this.progress = Math.round(event.loaded / event.total * 100);
            break;
          case HttpEventType.Response:
            this.modalService.openSnackBar('Profile Image has been uploaded successfully ', 'success');
            setTimeout(() => {
              this.progress = 0;
              this.dismiss(true);
              this.onSuccess.emit(true);
            }, 1500);
        }
      });
    }
   }

  initUploader() {
    this.uploader = new FileUploader({
      url: API_END_POINTS.uploads + '/users/' + this.user.userId + '/profile',
      disableMultipart : false,
      autoUpload: false,
      method: 'POST',
      itemAlias: 'media_file',
      allowedFileType: ['image'],
      queueLimit: 1,
    });
   }

   readFile(file: File) {
    const reader = new FileReader();
    reader.addEventListener('load', (e) => {
      this.mediaURL = reader.result;
    });
    reader.readAsDataURL(file);
  }

  removeItem(fileItem: FileItem) {
    this.mediaURL = null;
    fileItem.remove();
  }

  get status() {
    if (this.progress <= 25) {
      return 'danger';
    } else if (this.progress <= 50) {
      return 'warning';
    } else if (this.progress <= 75) {
      return 'info';
    } else {
      return 'success';
    }
  }
}
