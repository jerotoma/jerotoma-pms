import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
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
  currentClassAttendanceId: number;
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
  classAttendances: ClassAttendance[];

  constructor(
    private classAttendanceService: ClassAttendanceService,
    private attendanceStatusService: AttendanceStatusService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    private studentAttendanceService: StudentAttendanceService) {}

  ngOnInit() {
    this.loadForm();
    this.loadAttendanceStatuses();
    this.loadClassAttendances();
  }

  loadAttendanceStatuses() {
    this.isLoading = true;
    this.attendanceStatusService.loadAttendanceStatuses().subscribe((attendanceStatuses: AttendanceStatus[]) => {
      this.isLoading = false;
      if (attendanceStatuses) {
        const studentAttendances = this.classAttendance.studentAttendances;
        this.patch(studentAttendances);
        this.patchStudentValues();
        this.attendanceStatuses = attendanceStatuses; // This must be assigned at the last to make sure form is full loaded
      }
    });
  }

  loadClassAttendances() {
    this.isLoading = true;
    this.classAttendanceService.loadClassAttendances()
    .subscribe((classAttendances: ClassAttendance[]) => {
        this.isLoading = false;
        if (classAttendances) {
          this.classAttendances = classAttendances;
        }
    }, error => {
      this.isLoading = false;
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
      studentAttendanceStatusesArray: this.formBuilder.array([]),
    });
    this.onChanges();
  }

  patch(studentAttendances: StudentAttendance[]) {
    studentAttendances.forEach((studentAttendance: StudentAttendance, index: number) => {
      this.studentAttendanceStatusesArray.push(this.patchStudentAttendanceStatusValues(studentAttendance.studentId, studentAttendance.statusId));
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
        this.classAttendance = null;
        this.loadClassAttendancesByClassAttendanceId(classAttendanceId);
    });
  }

  findStudentAttendanceStatus(studentId: number) {
    return this.studentAttendanceStatuses.find((attendanceStatus: StudentAttendanceStatus) => {
      return attendanceStatus.studentId === studentId;
    });
  }

  loadClassAttendancesByClassAttendanceId(classAttendanceId: number) {
    this.isLoading = true;
    this.classAttendanceService.getClassAttendance(classAttendanceId)
    .subscribe((classAttendance: ClassAttendance) => {
        this.isLoading = false;
        if (classAttendance) {
          const studentAttendances = classAttendance.studentAttendances;
          this.patch(studentAttendances);
          this.classAttendance = classAttendance;
          this.patchStudentValues();
          this.currentClassAttendanceId = classAttendance.id;
        }
    }, error => {
      this.isLoading = false;
    });
  }

  get studentAttendanceStatusesArray(): FormArray {
    return this.recordAttendanceForm.get('studentAttendanceStatusesArray') as FormArray;
  }

  get hasStatusData(): boolean {
    return (this.attendanceStatuses) && (this.attendanceStatuses.length > 0) || !this.isLoading;
  }
}
