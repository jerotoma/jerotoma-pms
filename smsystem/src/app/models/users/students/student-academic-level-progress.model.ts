import { AcademicLevel } from '../../academic-levels/academic-level.model';
import { CompletionStatus } from '../../classes/classes.model';

export interface StudentAcademicLevelProgress {
  academicLevel: AcademicLevel;
  completionStatus: CompletionStatus;
  completionStatusName: string;
}
