import { Component, Input, OnInit } from '@angular/core';


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
          return this.userType === 'teacher';
        }
        get isUserStudent() {
          return this.userType === 'student';
        }

}
