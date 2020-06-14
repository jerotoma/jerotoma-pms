import { Room, AcademicYear, Teacher, Student, Course, MeetingTime } from 'app/models';


export interface ClassView {
  id: number;
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

export interface StudentClass {
  id: number;
  student: Student;
  academicYear: AcademicYear;
  jClasses: ClassView[];
  numberOfCourse: number;
}


export interface StudentClassAdmission {
  id: number;
  studentId: number;
  classIds: number[];
  academicYearId: number;
}
