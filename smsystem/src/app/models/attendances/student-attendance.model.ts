import { ClassView, AcademicYear } from 'app/models';

export interface StudentAttendance {
   class: ClassView;
   academicYear: AcademicYear;
   attendanceDate: Date;
}

export interface StudentAttendanceParam {
  classId: number;
  academicYearId: number;
  attendanceDate: Date;
}
