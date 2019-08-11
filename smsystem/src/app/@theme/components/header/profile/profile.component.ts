
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'app/services/auth';
import { UserService } from 'app/services/users/user.service';
import { User } from 'app/models/users/user';
import { getDeepFromObject } from 'app/utils';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  public user: User = {
    id: null,
    username: '',
    firstName: '',
    lastName: '',
    age: null,
    gender: 'female',
    occupation: '',
    birthDate: null,
    fullName: '',
    picture: '',
  };
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
      this.userService.getCurrentUser().subscribe((result: any) => {
        this.user = result.data;
      });
    }
  }
  getConfigValue(key: string): any {
    return getDeepFromObject(this.options, key);
  };

}
