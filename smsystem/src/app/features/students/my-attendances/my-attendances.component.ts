
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { PageEvent, MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { NbDialogService } from '@nebular/theme';
import { MatTableDataSource} from '@angular/material/table';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { ClassAttendance, Auth, ClassView,  AcademicLevel, User, Student} from 'app/models';
import { UserService, SecurityClearanceService, StudentClassService, AcademicLevelService } from 'app/services';
import { QueryParam, USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-my-attendances',
  templateUrl: './my-attendances.component.html',
  styleUrls: ['./my-attendances.component.scss'],
})
export class MyAttendancesComponent implements OnInit {

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'fullName', 'courseName',  'academicYearName', 'yearOfStudy', 'attendanceDate', 'action'];
  dataSource: MatTableDataSource<ClassAttendance> = new MatTableDataSource<ClassAttendance>();
  classAttendances: ClassAttendance[];
  jClasses: ClassView[];
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  studentClassForm: FormGroup;

  title: string = 'My Courses';
  student: Student;
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private academicLevelService: AcademicLevelService,
    private studentClassService: StudentClassService,
    private securityClearanceService: SecurityClearanceService) { }

  ngOnInit(): void {
    this.loadCurrentUser();
    this.loadAcademicLevels();
  }

  loadClassesByAcademicLevel(academicLevelId: number, studentId: number) {
    this.isLoading = true;
    this.studentClassService.loadClassesByStudentIDAndAcademicLevelID(academicLevelId, studentId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }

  loadCurrentUser() {
    this.securityClearanceService.loadCurrentUser();
    this.userService.getCurrentUser().subscribe((student: Student) => {
      this.student = student;
      this.loadClassesByAcademicLevel(student.academicLevelId, student.id);
    });
  }

  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      id: [null],
      academicLevelId: ['', Validators.required],
    });
    this.onChanges();
  }

  loadAcademicLevels() {
    this.academicLevelService.loadAcademicLevelList()
    .subscribe((academicLevels: AcademicLevel[]) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
        this.studentClassForm.patchValue({
          academicLevelId:  this.student.academicLevelId,
        }, {emitEvent: false});
      }
    });
  }

  onChanges() {
    this.studentClassForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      if (academicLevelId != null) {
        this.setCurrentAcademicLevel(academicLevelId);
        if (this.academicLevel) {
          this.loadClassesByAcademicLevel(this.academicLevel.id, this.student.id);
        }
      }
    });
  }

  setCurrentAcademicLevel(academicLevelId: number) {
    this.academicLevels.forEach(academicLevel => {
      if (academicLevel.id === academicLevelId) {
        this.academicLevel = academicLevel;
      }
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
