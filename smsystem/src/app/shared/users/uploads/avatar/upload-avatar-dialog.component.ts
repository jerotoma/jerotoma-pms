
import { Component, Input, EventEmitter, OnInit } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { FileUploader, FileItem } from 'ng2-file-upload';

import { AuthService } from 'app/services';
import { User } from 'app/models';
import { USER_TYPE, END_POINTS } from 'app/utils';

@Component({
  selector: 'app-upload-avatar-dialog',
  templateUrl: 'upload-avatar-dialog.component.html',
  styleUrls: ['./upload-avatar-dialog.component.scss'],
})
export class UploadAvatarDialogComponent implements OnInit {
  @Input('user') user: User;

  public uploader: FileUploader;
  mediaURL: any = null;
  uploadDir: string = 'users';
  formData: FormData;
  userAvatar: File;

  constructor(
    private authService: AuthService,
    protected ref: NbDialogRef<UploadAvatarDialogComponent>) {}

  ngOnInit(): void {
    this.initUploader();
  }

  dismiss(): void {
    this.ref.close();
  }

  public onFileSelected(event: EventEmitter<File[]>) {
    const file: File = event[0];
    this.userAvatar = file;
    this.readFile(file);
  }

  onSubmit() {
    if (this.userAvatar) {
      this.uploader.uploadAll();
    }
   }

  initUploader() {
    this.uploader = new FileUploader({
      url: END_POINTS.uploads + '/users/' + this.user.userId + '/profile',
      disableMultipart : false,
      autoUpload: false,
      method: 'POST',
      itemAlias: 'media_file',
      allowedFileType: ['image'],
      queueLimit: 1,
      authToken: this.authService.getAccessToken(),
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
}
