import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { ClassAttendance, AcademicLevel, Student} from 'app/models';
import { UserService, SecurityClearanceService, AttendanceReportService, AcademicLevelService } from 'app/services';
import { USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-attendance-report-details',
  templateUrl: './attendance-report-details.component.html',
  styleUrls: ['./attendance-report-details.component.scss'],
})
export class AttendanceReportDetailsComponent implements OnInit {

  isLoading: boolean = false;
  classAttendances: ClassAttendance[];
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  studentClassForm: FormGroup;

  classId: number = null;
  studentId: number = null;
  academicLevelId: number = null;
  title: string = 'My Attendance Record Details';
  student: Student;
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private attendanceReportService: AttendanceReportService,
    private academicLevelService: AcademicLevelService,
    private securityClearanceService: SecurityClearanceService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.classId = params['classId'];
      this.academicLevelId = params['academicLevelId'];
      this.studentId = params['studentId'];

    });
  }

  loadStudentAttendanceReportByClass(classId: number, studentId: number, academicLevelId: number) {
    this.isLoading = true;
    this.attendanceReportService.loadStudentClassAttendanceReportByClass(classId, studentId, academicLevelId).subscribe((classAttendances: ClassAttendance[]) => {
      this.classAttendances = classAttendances;
      this.isLoading = false;
    });
  }
}
