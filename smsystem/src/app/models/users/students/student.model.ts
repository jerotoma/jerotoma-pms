import { User  } from '../user.model';

export interface Student extends User {
  id: number;
  studentNumber: number;
  parentIds: number[];
  programId: number;
  programName: string;
  academicLevelId: number;
	currentAcademicLevelName: string;
}
