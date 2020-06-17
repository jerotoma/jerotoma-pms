import { StudentAttendance } from 'app/models';

export interface ClassAttendance {
  id: number;
  attendanceDate: Date;
  academicYearId: number;
  academicYearName: string;
  addedBy: number;
  classId: number;
  courseId: number;
  courseName: string;
  createdOn: Date;
  fullName: string;
  teacherId: number;
  updatedOn: Date;
  yearOfStudy: string;
  studentAttendances?: StudentAttendance[];
}

export interface ClassAttendanceParam {
  id: number;
  classId: number;
  academicYearId: number;
  attendanceDate: Date;
}
