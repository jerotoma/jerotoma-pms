import { StudentAcademicLevelProgress } from './student-academic-level-progress.model';


export interface StudentProgress {
  studentID: number;
  studentName: string;
  completedLevels: number;
  unCompletedLevels: number;
  requiredLevels: number;
  studentAcademicLevelProgresses: StudentAcademicLevelProgress[];
}
