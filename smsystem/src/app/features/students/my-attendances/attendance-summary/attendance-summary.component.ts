import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { AttendanceReport, AcademicLevel, Student} from 'app/models';
import { UserService, SecurityClearanceService, AttendanceReportService, AcademicLevelService } from 'app/services';
import { USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-attendance-summary',
  templateUrl: './attendance-summary.component.html',
  styleUrls: ['./attendance-summary.component.scss'],
})
export class AttendanceSummaryComponent implements OnInit {

  isLoading: boolean = false;
  attendanceReports: AttendanceReport[];
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  studentClassForm: FormGroup;

  baseURL: string = '/dashboard/my-attendances/report-details';
  title: string = 'My Attendance Records';
  student: Student;
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private attendanceReportService: AttendanceReportService,
    private academicLevelService: AcademicLevelService,
    private securityClearanceService: SecurityClearanceService) { }

  ngOnInit(): void {
    this.loadForm();
    this.loadCurrentUser();
  }

  loadClassesByAcademicLevel(academicLevelId: number, studentId: number) {
    this.isLoading = true;
    this.attendanceReportService.getAttendanceReports(studentId, academicLevelId).subscribe((attendanceReports: AttendanceReport[]) => {
      this.attendanceReports = attendanceReports;
      this.isLoading = false;
    });
  }

  loadCurrentUser() {
    this.securityClearanceService.loadCurrentUser();
    this.userService.getCurrentUser().subscribe((student: Student) => {
      this.student = student;
      this.loadClassesByAcademicLevel(student.academicLevelId, student.id);
      this.loadAcademicLevels(student);
    });
  }

  onViewDetails(event: Event, attendanceReport: AttendanceReport) {
    event.preventDefault();
    // Set our navigation extras object
    // that contains our global query params and fragment
    const navigationExtras: NavigationExtras = {
      queryParams: {
        classId: attendanceReport.classId,
        studentId: attendanceReport.studentId,
        academicLevelId: attendanceReport.academicLevelId,
      },
    };
    this.router.navigate([this.baseURL], navigationExtras);
  }

  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      academicLevelId: [null, Validators.required],
    });
    this.onChanges();
  }

  loadAcademicLevels(student: Student) {
    this.academicLevelService.loadAcademicLevelList()
    .subscribe((academicLevels: AcademicLevel[]) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
        this.setCurrentAcademicLevel(student.academicLevelId);
        this.studentClassForm.patchValue({
          academicLevelId: student.academicLevelId,
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

  preventDefaultJClass(event: any) {
    event.preventDefault();
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
