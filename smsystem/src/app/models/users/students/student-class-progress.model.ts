import { StudentClass, AcademicLevel, AcademicYear } from 'app/models';


export interface StudentClassesProgress {
  studentID: number;
  studentName: string;
  academicLevel: AcademicLevel;
  academicYear: AcademicYear;
  studentClasses: StudentClass[];
}
