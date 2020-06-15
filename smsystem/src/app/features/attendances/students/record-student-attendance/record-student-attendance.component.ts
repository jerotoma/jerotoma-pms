import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray } from '@angular/forms';

import {
  ClassView,
  Student,
  AcademicYear,
  StudentAttendanceParam,
  AttendanceStatus,
  StudentAttendanceStatus,
  ClassAttendance,
  StudentAttendance,
} from 'app/models';

import {
  ModalService,
  ClassAttendanceService,
  StudentAttendanceService,
  AttendanceStatusService,
} from 'app/services';

@Component({
  selector: 'app-record-student-attendance',
  templateUrl: './record-student-attendance.component.html',
  styleUrls: ['./record-student-attendance.component.scss'],
})
export class RecordStudentAttendenceComponent implements OnInit {

  @Input() title: string;
  @Output() onRecordSubmitted = new EventEmitter();
  @Input() id: string;
  @Input() classAttendance: ClassAttendance;

  currentDate: Date = new Date();
  recordAttendanceForm: FormGroup;
  isSubmitting: boolean = false;
  isLoading: boolean = false;
  jClasses: ClassView[];
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  studentAttendanceParam: StudentAttendanceParam;
  studentAttendanceStatuses: StudentAttendanceStatus[] = [];
  studentAttendance: StudentAttendance;
  attendanceStatuses: AttendanceStatus[];

  constructor(
    private classAttendanceService: ClassAttendanceService,
    private attendanceStatusService: AttendanceStatusService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    private studentAttendanceService: StudentAttendanceService) {}

  ngOnInit() {
    this.loadForm();
    this.loadAttendanceStatuses();
  }

  loadAttendanceStatuses() {
    this.isLoading = true;
    this.attendanceStatusService.loadAttendanceStatuses().subscribe((attendanceStatuses: AttendanceStatus[]) => {
      this.isLoading = false;
      if (attendanceStatuses) {
        this.attendanceStatuses = attendanceStatuses;
        const students = this.classAttendance.students;
        this.patch(students, attendanceStatuses);
        this.patchStudentValues();
      }
    });
  }

  onSubmit() {
    this.studentAttendanceParam = this.recordAttendanceForm.value;
    this.studentAttendanceService.createStudentAttendance(this.studentAttendanceParam)
    .subscribe((studentAttendance: StudentAttendance) => {
      if (studentAttendance) {
        this.modalService.openSnackBar('Attendance has been recorded', 'success');
        this.onRecordSubmitted.emit({success: true});
      }
    });
  }

  loadForm() {
    this.recordAttendanceForm = this.formBuilder.group({
      id: [null],
      classAttendanceId: [null, Validators.required],
      studentAttendanceStatuses: this.formBuilder.array([]),
    });
    this.onChanges();
  }

  patch(students: Student[], attendanceStatuses: AttendanceStatus[]) {
    const studentAttendanceStatusesControl = <FormArray>this.recordAttendanceForm.get('studentAttendanceStatuses');
    students.forEach((student: Student, index: number) => {
      studentAttendanceStatusesControl.push(this.patchStudentAttendanceStatusValues(student.id, attendanceStatuses[0].id));
    });
  }
  patchStudentAttendanceStatusValues(studentId: number, attendanceStatusId: number) {
    return this.formBuilder.group({
      studentId: [studentId, Validators.required],
      attendanceStatusId: [attendanceStatusId, Validators.required],
    });
  }
  patchStudentValues() {
    this.recordAttendanceForm.patchValue({
      classAttendanceId: this.classAttendance.id,
    }, {emitEvent: false});
  }

  onChanges() {
    this.recordAttendanceForm.controls['classAttendanceId'].valueChanges.subscribe((classAttendanceId: number) => {
        // this.loadClassAttendancesByClassAttendanceId(classAttendanceId);
    });
  }

  findStudentAttendanceStatus(studentId: number) {
    return this.studentAttendanceStatuses.find((attendanceStatus: StudentAttendanceStatus) => {
      return attendanceStatus.studentId === studentId;
    });
  }

  get hasStatusData(): boolean {
    return (this.attendanceStatuses) && (this.attendanceStatuses.length > 0) || !this.isLoading;
  }
}
