import { Room, AcademicYear, AcademicLevel, Teacher, Student, Course, MeetingTime, ScoreStanding } from 'app/models';


export interface ClassView {
  id: number;
  totalStudents: number;
  teacher: Teacher;
  course: Course;
  meetingTime: MeetingTime;
  academicYear: AcademicYear;
  students?: Student[];
  room: Room;
  capacity: number;
}

export interface ClassAdmission {
  id: number;
  teacher: Teacher;
  course: Course;
  academicYear: AcademicYear;
  meetingTime: MeetingTime;
  room: Room;
  capacity: number;
}

export interface StudentAcademicLevel {
  id: number;
  student: Student;
  academicYear: AcademicYear;
  academicLevel: AcademicLevel;
  jClasses: ClassView[];
  completionStatus: CompletionStatus;
  completionStatusName: string;
  classesCount: number;
}

export enum CompletionStatus {
  IN_PROGRESS = 'In Progress',
  PENDING = 'Pending',
  NOT_STARTED = 'Not Started',
  COMPLETED = 'Completed',
}


export interface StudentClassAdmission {
  id: number;
  studentId: number;
  classIds: number[];
  academicYearId: number;
  academicLevelId: number;
}

export interface StudentAcademicLevelClass {
  student: Student;
  academicYear: AcademicYear;
  academicLevel: AcademicLevel;
  classes: ClassView[];
}

export interface StudentClassParam {
  studentId?: number;
  academicYearId?: number;
  academicLevelId?: number;
  programId?: number;
}

export interface TeacherClassParam {
  teacherId?: number
  academicYearId?: number;
  academicLevelId?: number;
  programId?: number;
}

export interface StudentClass {
  classId?: number;
  studentAcademicLevelId?: number;
  student?: Student;
  status?: string;
  statusId?: number;
  score?: number;
  scoreStanding?: ScoreStanding;
}
