import { AcademicLevel } from '../academic-levels';

export interface Program {
  id: number;
  name: string;
  code: string;
  description: string;
  academicLevelIDs: number[];
  academicLevels: AcademicLevel[];
  createdOn?: Date;
  updatedOn?: Date;
}


export interface ProgramaAcademicLevelPrerequisiteParam {
  programId: number;
  academicLevelId: number;
  academicLevelPrerequisiteIds: number[];
}
