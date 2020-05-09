import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { Room, ShowMessage } from 'app/models';
import { RoomService } from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-room-create',
  templateUrl: 'room-create.component.html',
  styleUrls: ['room-create.component.scss'],
})
export class RoomCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();

  @Input() name: string = '';
  @Input() code: string = '';
  @Input() id: string = '0';
  @Input() description: string = '';
  @Input() capacity: string = '';

  schoolClassForm: FormGroup;
  room: Room;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private roomService:  RoomService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<RoomCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    if (this.action === 'edit') {
        this.patchSchoolClass();
    }
  }
  patchSchoolClass() {
    this.schoolClassForm.patchValue({
      name: this.name,
      description: this.description,
      code: this.code,
      capacity: parseInt(this.capacity, 10),
      id: parseInt(this.id, 10),
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.room = this.schoolClassForm.value;
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updateSchoolClass();
    } else {
      this.roomService.createRoom(this.room)
      .subscribe((result: Room) => {
        if (result) {
          this.schoolClassForm.reset();
          this.dismiss();
        }
      });
    }

  }
  updateSchoolClass() {
    this.roomService.updateRoom(this.room)
          .subscribe((result: Room) => {
            if (result) {
              this.schoolClassForm.reset();
              this.dismiss();
            }
          });
    }
  getDescriptionContent(description: string) {
   if (description) {
    this.schoolClassForm.patchValue({
      description: description,
    });
    }
  }

  loadForm() {
    this.schoolClassForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required],
      code: ['', Validators.required],
      capacity: ['', Validators.compose([Validators.required])],
      description: [''],
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
      userType: 'schoolClass',
    };
  }

}
