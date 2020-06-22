
import { Component, OnInit, EventEmitter} from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { FileUploader } from 'ng2-file-upload';
import { UploadService } from 'app/services';
import { NbDialogRef } from '@nebular/theme';
import { END_POINTS } from 'app/utils';

@Component({
  selector: 'app-uploads',
  templateUrl: 'uploads.component.html',
  styleUrls: ['uploads.component.scss'],
})
export class UploadsComponent implements OnInit {
  id: string = '0';
  title: string = '';
  name: string = '';
  action: string = '';
  uploadType: string = '';
  confirmed: boolean = false;
  uploadDir: string = 'users';
  formData: FormData;
  uploadForm: FormGroup;

  public hasBaseDropZoneOver: boolean = false;
  public hasAnotherDropZoneOver: boolean = false;

  public uploader: FileUploader = new FileUploader({
    url: END_POINTS.uploads,
    disableMultipart : false,
    autoUpload: false,
    method: 'post',
    itemAlias: 'media_files',
    allowedFileType: ['image', 'pdf'],
  });

  constructor(
    private formBuilder: FormBuilder,
    private uploadService: UploadService,
    protected ref: NbDialogRef<UploadsComponent>) {}

  public onFileSelected(event: EventEmitter<File[]>) {
    const file: File = event[0];
    window.console.log(file);
  }

  public fileOverBase(e: any): void {
    this.hasBaseDropZoneOver = e;
  }

  public fileOverAnother(e: any): void {
    this.hasAnotherDropZoneOver = e;
  }

  error: string;
  uploadResponse = { status: '', message: '', filePath: '' };

  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      media_files: [''],
    });
  }
  dismiss() {
    this.ref.close({
      id: parseInt(this.id, 10),
      uploadType: this.uploadType,
      formData: this.formData,
      confirmed: this.confirmed,
    });
  }
  onConfirmed() {
    this.confirmed = true;
    this.dismiss();
  }
  onSubmit() {
   this.uploader.uploadAll();
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      window.console.log(file);
      this.uploadForm.get('media_files').setValue(file);
    }
  }

}
