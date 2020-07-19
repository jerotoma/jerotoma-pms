
import { Injectable } from '@angular/core';
import { AuthService } from 'app/services/auth';
import { USER_ROLE, User, Auth } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class SecurityClearanceService {
  private userRoles: USER_ROLE[];
  private isLoading: boolean = false;

  constructor(private authService: AuthService) {}

  get hasResult() {
    return this.isLoading;
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

  get isTeacher() {
    return this.userRoles && this.userRoles.indexOf(USER_ROLE.TEACHER) !== -1;
  }

  loadCurrentUser() {
    this.authService.getAuthenticatedUser().subscribe((auth: Auth) => {
      this.userRoles = auth.roles;
      this.isLoading = true;
    });
  }

  distroyCurrentUser() {
    this.userRoles = null;
    this.isLoading = false;
  }
}
