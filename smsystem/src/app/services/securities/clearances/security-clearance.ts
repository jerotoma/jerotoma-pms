
import { Injectable } from '@angular/core';
import { AuthService } from 'app/services/auth';
import { USER_ROLE } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class SecurityClearanceService {

 private userRoles: USER_ROLE[];

  constructor(private authService: AuthService) {
    this.loadCurrentUser();
  }

  get isAdmin() {
      return this.userRoles && this.userRoles.indexOf(USER_ROLE.ADMIN) !== -1;
  }

  get isStudent() {
    return this.userRoles && this.userRoles.indexOf(USER_ROLE.STUDENT) !== -1;
  }

  get isStaff() {
    return this.userRoles && this.userRoles.indexOf(USER_ROLE.STAFF) !== -1;
  }


  get isParent() {
    return this.userRoles && this.userRoles.indexOf(USER_ROLE.PARENT) !== -1;
  }

  loadCurrentUser() {
    this.userRoles = this.authService.loadCurrentUserRoles();
  }
}
