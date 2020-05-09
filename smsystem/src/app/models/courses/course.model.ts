import { AcademicYear, AcademicDiscipline, Department } from 'app/models';

export interface Course {
  id: number;
  code: string;
  name?: string;
  description: string;
  academicYear?: AcademicYear;
  academicDisciplines: AcademicDiscipline[];
  department: Department;
}
