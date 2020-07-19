import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { UploadAvatarDialogComponent } from 'app/shared/users/uploads';
import { NbDialogService } from '@nebular/theme';

import { User, Student, Parent, Teacher, Staff } from 'app/models';
import { USER_TYPE, API_END_POINTS } from 'app/utils';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent implements OnInit {
    @Input('userDatail') userDatail: Student | Teacher | Parent | Staff | any = {};
    @Input('userType') userType: string = USER_TYPE.TEACHER;
    @Output() onImageChangeSuccess: EventEmitter<any> = new EventEmitter<any>();
    baseURL: string = API_END_POINTS.baseURL;

    constructor(private dialogService: NbDialogService) {}

    ngOnInit() {
      // window.console.log(this.userDatail);
    }

    onSubmit() {

    }

    changeProfilePic() {
     this.openDialog();
    }
    openDialog(): void {
      this.dialogService.open(UploadAvatarDialogComponent, {
        context: {
          user: this.userDatail,
        },
      }).onClose.subscribe(data => {
        this.onImageChangeSuccess.emit({
          succes: true,
          id: this.userDatail.id,
        });
      });
    }

    fileChange(event: any) {
      const fileList: FileList = event.target.files;
      if (fileList.length > 0) {
          const file: File = fileList[0];
          const formData: FormData = new FormData();
          formData.append('uploadFile', file, file.name);
      }
    }

    get isUserTeacher() {
      return this.userType === USER_TYPE.TEACHER;
    }

    get isUserStudent() {
      return this.userType === USER_TYPE.STUDENT;
    }

    get isUserParent() {
      return this.userType === USER_TYPE.PARENT;
    }

    get isUserStaff() {
      return this.userType === USER_TYPE.STAFF;
    }

}
