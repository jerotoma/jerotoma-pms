import { Component, OnInit } from '@angular/core';
import { Auth } from 'app/models';
import { AuthService, SecurityClearanceService } from 'app/services';

@Component({
  selector: 'app-mycourses',
  templateUrl: './my-courses.component.html',
  styleUrls: ['./my-courses.component.scss'],
})
export class MyCoursesComponent implements OnInit {
  auth: Auth;

  constructor(
    private authService: AuthService,
    private securityClearanceService: SecurityClearanceService) { }

  ngOnInit(): void {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    this.securityClearanceService.loadCurrentUser();
    this.authService.getAuthenticatedUser().subscribe((auth: Auth) => {
      this.auth = auth;
    });
  }

  get hasResult() {
    return this.securityClearanceService.hasResult;
  }
  get isStudent() {
    return this.securityClearanceService.isStudent;
  }

  get isTeacher() {
    return this.securityClearanceService.isTeacher;
  }
}
