import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { ClassAttendance, Student, ClassView, AttendanceReport} from 'app/models';
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
  attendanceReports: AttendanceReport[];
  classView: ClassView;
  classViews: ClassView[];
  studentAttendanceReportForm: FormGroup;

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
      this.loadStudentAttendanceReportByClass(this.classId, this.studentId);
    });
    this.loadForm();
  }

  loadStudentAttendanceReportByClass(classId: number, studentId: number) {
    this.isLoading = true;
    this.attendanceReportService.loadStudentClassAttendanceReportByClass(classId, studentId).subscribe((classAttendances: ClassAttendance[]) => {
      this.classAttendances = classAttendances;
      this.isLoading = false;
    });
  }

  loadForm() {
    this.studentAttendanceReportForm = this.formBuilder.group({
      classId: [null, Validators.required],
    });
    this.onChanges();
  }

  onChanges() {
    this.studentAttendanceReportForm.get('classId').valueChanges.subscribe((classId: number) => {
      if (classId != null) {
        this.setCurrentClass(classId);
        if (this.classId) {
          this.loadStudentAttendanceReportByClass(this.classId, this.studentId);
        }
      }
    });
  }

  setCurrentClass(classId: number) {
    this.classViews.forEach(classView => {
      if (classView.id === classId) {
        this.classView = classView;
      }
    });
  }

  loadClassesByAcademicLevel(academicLevelId: number, studentId: number) {
    this.isLoading = true;
    this.attendanceReportService.getAttendanceReports(studentId, academicLevelId).subscribe((attendanceReports: AttendanceReport[]) => {
      this.attendanceReports = attendanceReports;
      this.isLoading = false;
    });
  }
}
