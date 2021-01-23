import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { UploadAvatarDialogComponent } from 'app/shared/users/uploads';
import { NbDialogService } from '@nebular/theme';
import { AddParentComponent } from '../parents/add-parent/add-parent.component';

import { UserService } from 'app/services';
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
    @Input('userLabel') userLabel: string = 'User';
    @Output() onImageChangeSuccess: EventEmitter<any> = new EventEmitter<any>();
    baseURL: string = API_END_POINTS.baseURL;
    parents: Parent[] = [];
    students: Student[] = [];

    constructor(private dialogService: NbDialogService,
      private userService: UserService) {}

    ngOnInit() {
     this.loadData();
    }

    loadData() {
     if (this.isUserStudent) {
       this.loadStudentParents();
     } else if (this.isUserParent) {
       this.loadParentStudents();
     }
    }

    loadStudentParents() {
      this.userService.loadParentsByStudentID(this.userDatail.id).subscribe((parents: Parent[]) => {
        this.parents = parents;
      });
    }

    loadParentStudents() {
      this.userService.loadStudentsByParentID(this.userDatail.id).subscribe((students: Student[]) => {
        this.students = students;
      });
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
          userId: this.userDatail.userId,
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

    addParent(student: Student) {
      this.dialogService.open(AddParentComponent, {
        context: {
          title: 'Add New Parent',
          student: student,
        },
      }).onClose.subscribe(data => {
        this.loadData();
      });
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
