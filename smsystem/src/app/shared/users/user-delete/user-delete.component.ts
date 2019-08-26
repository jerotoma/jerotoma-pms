import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NbDialogRef } from '@nebular/theme';
import { UserService } from 'app/services/users';
import { ShowMessage } from 'app/models/messages/show-message.model';
@Component({
  selector: 'app-user-delete',
  templateUrl: 'user-delete.component.html',
  styleUrls: ['user-delete.component.scss'],
})
export class UserDeleteComponent implements OnInit {
  userId: string = '0';
  title: string = '';
  name: string = '';
  action: string = '';
  userType: string = '';
  confirmed: boolean = false;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  constructor(
    private userService:  UserService,
    protected ref: NbDialogRef<UserDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteUser(teacherId: number) {
    if (this.confirmed) {
      this.userService.deleteUser(teacherId, this.userType)
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const data = resp.body;
        const status = resp.status;
        if (status !== null && status === 200) {
          this.dismiss();
        } else {
          this.showMessage.success = false;
          this.showMessage.error = true;
          this.showMessage.message = data  ? data.message : '';
        }
      }, error => {
        this.showMessage.error = true;
        this.showMessage.success = false;
        this.showMessage.message = error ? error.error.message : '';
      });
    }
  }

  dismiss() {
    this.ref.close();
  }
  onConfirmed() {
    this.confirmed = true;
    this.deleteUser(parseInt(this.userId, 10));
  }

}
