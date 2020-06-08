import { ClassView, AcademicYear } from 'app/models';

export interface StaffAttendance {
   class: ClassView;
   academicYear: AcademicYear;
   attendanceDate: Date;
}

export interface StaffAttendanceParam {
  classId: number;
  academicYearId: number;
  attendanceDate: Date;
}
