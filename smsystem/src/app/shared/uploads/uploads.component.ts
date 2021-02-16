
import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpEvent, HttpEventType } from '@angular/common/http';

import { FileUploader, FileItem } from 'ng2-file-upload';
import { UploadService, AuthService, ModalService } from 'app/services';
import { NbDialogRef } from '@nebular/theme';
import { API_END_POINTS, APP_ACTION_TYPE } from 'app/utils';

export interface UploadFile {
  file: File;
  dataURL: string | ArrayBuffer;
}

@Component({
  selector: 'app-uploads',
  templateUrl: 'uploads.component.html',
  styleUrls: ['uploads.component.scss'],
})
export class UploadsComponent implements OnInit {
  @Input('uploadDir') uploadDir: string = 'users';
  @Input('uploadURL') uploadURL: string = API_END_POINTS.uploads;
  @Input('title') title: string = '';
  @Input('action') action: string = APP_ACTION_TYPE.create;
  @Input('uploadLimit') uploadLimit: number = 10;
  @Output() onSuccess: EventEmitter<boolean> = new EventEmitter<boolean>();

  formData: FormData;
  uploadForm: FormGroup;
  public uploader: FileUploader;
  uploadFiles: UploadFile[] = [];
  userAvatar: File;
  progress: number = 0;

  public hasBaseDropZoneOver: boolean = false;
  public hasAnotherDropZoneOver: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private modalService: ModalService,
    private uploadService: UploadService,
    protected ref: NbDialogRef<UploadsComponent>) {}

    ngOnInit(): void {
      this.loadForm();
      this.initUploader();
    }

    dismiss(confirmed: boolean): void {
      this.uploadFiles = null;
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
      if (this.uploadFiles) {
        const formData = new FormData();
        this.uploadFiles.forEach((uploadFile: UploadFile, index: number) => {
          formData.append('upload_files[]', uploadFile.file);
        });
        this.uploadService.uploadFileTrackProgress(formData, this.uploadURL).subscribe((event: HttpEvent<any>) => {
          switch (event.type) {
            case HttpEventType.Sent:
              break;
            case HttpEventType.ResponseHeader:
              break;
            case HttpEventType.UploadProgress:
              this.progress = Math.round(event.loaded / event.total * 100);
              break;
            case HttpEventType.Response:
              if (this.uploadFiles.length > 1) {
                this.modalService.openSnackBar('Files have been uploaded successfully', 'success');
              } else {
                this.modalService.openSnackBar('File has been uploaded successfully ', 'success');
              }
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
        url: this.uploadURL,
        disableMultipart : false,
        autoUpload: false,
        queueLimit: this.uploadLimit,
        authToken: this.authService.getAccessToken(),
      });
     }

    readFile(file: File) {
      const reader = new FileReader();
      reader.addEventListener('load', (e) => {
        this.uploadFiles.push({
          file: file,
          dataURL: reader.result,
        });
      });
      reader.readAsDataURL(file);
    }

    removeItem(fileItem: FileItem) {
      this.uploadFiles.forEach((uploadFile: UploadFile, index: number) => {
        if (fileItem._file === uploadFile.file) {
          this.uploadFiles.splice(index, 1);
        }
      });
      fileItem.remove();
    }

    loadForm() {
      this.uploadForm = this.formBuilder.group({

      });
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
