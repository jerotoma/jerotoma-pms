export interface StudentAttendance {
  academicYearId: number;
  academicYearName: string;
  attendanceStatusId: number;
  classAttendanceId: number;
  classId: number;
  courseId: number;
  courseName: string;
  createdOn: Date;
  fullName: string;
  id: number;
  statusId: number;
  statusName: string;
  studentId: number;
  updatedOn: Date;
  yearOfStudy: Date;
}

export interface StudentAttendanceParam {
  classAttendanceId: number;
  studentAttendanceStatuses: StudentAttendanceStatus[];
}

export interface StudentAttendanceStatus {
  studentId: number;
  attendanceStatusId: number;
}
