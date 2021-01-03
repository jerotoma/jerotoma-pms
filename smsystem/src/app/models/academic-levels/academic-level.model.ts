export interface AcademicLevel {
  id: number;
  name: string;
  code: string;
  description: string;
  createdOn?: Date;
  updatedOn?: Date;
  prerequisites?: AcademicLevelPrerequisite[];
}

export interface AcademicLevelPrerequisite {
  id: number;
	academicLevel: AcademicLevel;
	programId: number;
}

export interface AcademicLevelCompletionOrder {
  academicLevelId: number;
  completionOrderId: number;
}
