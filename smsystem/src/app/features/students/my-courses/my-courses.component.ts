import { Component, OnInit } from '@angular/core';
import { Auth, Student, Teacher, User } from 'app/models';
import { AuthService, UserService,  SecurityClearanceService } from 'app/services';
import { USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-mycourses',
  templateUrl: './my-courses.component.html',
  styleUrls: ['./my-courses.component.scss'],
})
export class MyCoursesComponent implements OnInit {
  auth: Auth;
  title: string = 'My Courses';
  student: Student;
  teacher: Teacher;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private securityClearanceService: SecurityClearanceService) { }

  ngOnInit(): void {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    this.securityClearanceService.loadCurrentUser();
    this.authService.getAuthenticatedUser().subscribe((auth: Auth) => {
      this.auth = auth;
      this.loadUserDetail(auth.userId);
    });
  }

  loadUserDetail(userId: number) {

    if (this.isStudent) {
      this.userService.getUser(userId).subscribe((user: Student) => {
        this.student = user;
      });
    }

    if (this.isTeacher) {
      this.userService.getUser(userId).subscribe((teacher: Teacher) => {
        this.teacher = teacher;
      });
    }
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
