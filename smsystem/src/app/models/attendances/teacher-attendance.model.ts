import { ClassView, AcademicYear } from 'app/models';

export interface TeacherAttendance {
   class: ClassView;
   academicYear: AcademicYear;
   attendanceDate: Date;
}

export interface TeacherAttendanceParam {
  classId: number;
  academicYearId: number;
  attendanceDate: Date;
}
