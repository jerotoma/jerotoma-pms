import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { Stream, AcademicLevel } from 'app/models';
import {
  StreamService,
  AcademicLevelService,
  ModalService,
} from 'app/services';
import { QueryParam, OPEN_CLOSE_ANIMATION } from 'app/utils';

@Component({
  selector: 'app-add-stream',
  animations: OPEN_CLOSE_ANIMATION,
  templateUrl: `./add-stream.component.html`,
  styleUrls: [`./add-stream.component.scss`],
})
export class AddStreamComponent implements OnInit {
  title: string;
  academicLevel: AcademicLevel;

  isLoading: boolean;
  isSubmitting: boolean = false;

  streams: Stream[];
  streamIds: number[] = [];
  academicLevelStreamForm: FormGroup;

  constructor(
    private streamService: StreamService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    private academicLevelService: AcademicLevelService,
    protected ref: NbDialogRef<AddStreamComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadStreams();
  }

  loadForm() {
    this.academicLevelStreamForm = this.formBuilder.group({
      academicLevelId: [null, Validators.required ],
      streamIds: [null, Validators.required],
    });

    if (this.academicLevel) {
      this.academicLevelStreamForm.patchValue({
        academicLevelId: this.academicLevel.id,
      }, {emitEvent: false});
    }
  }

  onSubmit() {
    this.isSubmitting = true;
    this.academicLevelService.addStreamsToAcademicLevel(this.academicLevelStreamForm.value)
        .subscribe((academicLevel: AcademicLevel) => {
          this.isSubmitting = false;
            if (academicLevel) {
              this.modalService.openSnackBar('Stream(s) has been added to Academic Level', 'success');
              this.ref.close({confirmed: true});
            }
        });
  }
  dismiss() {
    this.ref.close({confirmed: false});
  }

  loadStreams() {
    this.isLoading = true;
    this.streamService.loadStreams()
      .subscribe((streams: Stream[]) => {
        this.isLoading = false;
        if (streams) {
          this.streams = streams;
        }
      });
  }

  checkedChange(checked: boolean, academicLevel: Stream) {
    if (checked) {
      this.streamIds.push(academicLevel.id);
    } else {
      for (let i = 0; i < this.streamIds.length; i++) {
        if ( this.streamIds[i] === academicLevel.id) {
          this.streamIds.splice(i, 1);
        }
     }
    }
    this.academicLevelStreamForm.patchValue({
      streamIds: this.streamIds,
    }, {emitEvent: false});

  }
}
