import { StudentClass, AcademicLevel, AcademicYear } from 'app/models';


export interface StudentClassesProgress {
  studentID: number;
  studentName: string;
  classesProgress: ClassProgress[];
}

export interface ClassProgress {
  academicLevel: AcademicLevel;
  academicYear: AcademicYear;
  studentClasses: StudentClass[];
}
