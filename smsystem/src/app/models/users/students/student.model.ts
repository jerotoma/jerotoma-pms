import { User  } from '../user.model';
import { Parent  } from '../parents/parent.model';

export interface Student extends User {
  id: number;
  studentNumber: number;
  primaryParent: Parent;
  programId: number;
  programName: string;
  academicLevelId: number;
  streamId?: number;
  streamName?: string;
  currentAcademicLevelName: string;
}
