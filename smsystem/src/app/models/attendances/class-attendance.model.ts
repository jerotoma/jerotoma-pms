import { ClassView, AcademicYear } from 'app/models';

export interface ClassAttendance {
  id: number;
  mclass: ClassView;
  academicYear: AcademicYear;
  attendanceDate: Date;
}

export interface ClassAttendanceParam {
  id: number;
  classId: number;
  academicYearId: number;
  attendanceDate: Date;
}
