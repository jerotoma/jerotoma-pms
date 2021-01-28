import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { Stream, AcademicLevel } from 'app/models';
import {
  StreamService,
  ModalService,
} from 'app/services';
import { QueryParam, APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-stream-create',
  templateUrl: `./stream-create.component.html`,
  styleUrls: [`./stream-create.component.scss`],
})
export class StreamCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = APP_ACTION_TYPE.create;
  @Input() academicLevel: AcademicLevel;

  streamForm: FormGroup;
  stream: Stream;
  listDisplay: string = 'none';
  isSubmitting: boolean = false;

  constructor(
    private streamService:  StreamService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<StreamCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === APP_ACTION_TYPE.edit) {
        this.loadStream();
    }

  }
  patchStream() {
    this.streamForm.patchValue({
      name: this.stream.name,
      description: this.stream.description,
      code: this.stream.code,
      id: this.stream.id,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.stream = this.streamForm.value;
        if (this.action === 'edit') {
            this.updateStream();
        } else {
          this.streamService.createStream(this.stream)
              .subscribe((stream: Stream ) => {
                this.isSubmitting = false;
                  if (stream) {
                    this.stream = stream;
                    this.modalService.openSnackBar('Stream ' + stream.name + ' has been created', 'success');
                    this.dismiss();
                  }
              });
        }

  }
  updateStream() {
    this.streamService.updateStream(this.stream)
        .subscribe((stream: Stream ) => {
          this.isSubmitting = false;
          if (stream) {
            this.stream = stream;
            this.modalService.openSnackBar('Stream ' + stream.name + ' has been updated', 'success');
            this.dismiss();
          }
      });
    }
  getDescriptionContent(description: string) {
   if (description) {
      this.streamForm.patchValue({
        description: description,
      });
    }
  }

  loadForm() {
    this.streamForm = this.formBuilder.group({
      id: [null],
      academicLevelId: [null],
      name: ['', Validators.required ],
      code: ['', Validators.required ],
      description: [''],
    });

    if (this.academicLevel) {
      this.streamForm.patchValue({
        academicLevelId: this.academicLevel.id,
      });
    }
  }

  loadStream() {
    this.streamService.getStream(this.stream.id).subscribe((stream: Stream) => {
      if (stream) {
        this.stream = stream;
        this.patchStream();
      }
    });
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'stream',
    };
  }
}
