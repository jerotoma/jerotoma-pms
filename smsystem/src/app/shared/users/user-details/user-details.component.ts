import { Component, Input, OnInit } from '@angular/core';
import { UploadAvatarDialogComponent } from 'app/shared/users/uploads';
import { NbDialogService } from '@nebular/theme';

import { User } from 'app/models';
import { USER_TYPE, END_POINTS } from 'app/utils';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent implements OnInit {
    @Input('userDatail') userDatail: User = {};
    @Input('userType') userType: string = USER_TYPE.teacher;

    constructor(private dialogService: NbDialogService){}

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
      return this.userType === USER_TYPE.teacher;
    }

    get isUserStudent() {
      return this.userType === USER_TYPE.student;
    }

    get isUserParent() {
      return this.userType === USER_TYPE.parent;
    }

    get isUserStaff() {
      return this.userType === USER_TYPE.staff;
    }

}
