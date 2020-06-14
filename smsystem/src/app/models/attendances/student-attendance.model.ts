import { ClassView, AcademicYear, AttendanceStatus } from 'app/models';

export interface StudentAttendance {
   class: ClassView;
   academicYear: AcademicYear;
   attendanceDate: Date;
   attendanceStatus: AttendanceStatus;
}

export interface StudentAttendanceParam {
  classAttendanceId: number;
  studentAttendanceStatuses: StudentAttendanceStatus[];
}

export interface StudentAttendanceStatus {
  studentId: number;
  attendanceStatusId: number;
}
