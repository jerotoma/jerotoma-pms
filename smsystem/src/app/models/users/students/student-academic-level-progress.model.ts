import { AcademicLevel } from 'app/models/academic-levels/academic-level.model';
import { CompletionStatus } from 'app/models/classes/classes.model';
import { AcademicYear } from 'app/models/academic-years';

export interface StudentAcademicLevelsProgress {
  studentID: number;
  studentName: string;
  completedLevels: number;
  unCompletedLevels: number;
  requiredLevels: number;
  academicLevelProgresses: AcademicLevelProgress[];
}

export interface AcademicLevelProgress {
  academicLevel: AcademicLevel;
  academicYear: AcademicYear;
  completionStatus: CompletionStatus;
  completionStatusName: string;
}
