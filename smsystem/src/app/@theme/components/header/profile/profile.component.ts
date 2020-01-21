
import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { AuthService } from 'app/services/auth';
import { UserService } from 'app/services/users/user.service';
import { User, Token } from 'app/models';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  public user: User = null;
  protected options: {};

  constructor(
    private userService: UserService,
    private authService: AuthService,
    ) { }

  ngOnInit() {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    if (this.authService.isAuthenticated()) {
      this.authService.getToken().subscribe((token: Token) => {
        this.loadUser(token.sub);
      });
    }
  }

  loadUser(username: string) {
    if (username) {
      this.userService.loadUserByUsername(username, 'teacher').subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        window.console.log(resp.body.data);
        const status = resp.status;
          if (status !== null && status === 200) {
            this.user = resp.body.data;
          }
      }, error => {
        window.console.log(error);
      });
    }
  }
}
