import { Component, Input, OnInit } from '@angular/core';
import { USER_TYPE } from '../user-type.constant';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent implements OnInit {
        @Input('userDatail') userDatail: any = {};
        @Input('userType') userType: string = 'teacher';

        ngOnInit() {
          window.console.log(this.userDatail);
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
