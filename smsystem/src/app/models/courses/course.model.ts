import { AcademicLevel, Program, AcademicDiscipline, Department } from 'app/models';

export interface Course {
  id: number;
  code: string;
  name?: string;
  description: string;
  academicLevel?: AcademicLevel;
  program?: Program;
  academicDisciplines: AcademicDiscipline[];
  department: Department;
}
