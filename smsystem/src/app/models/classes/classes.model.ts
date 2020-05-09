import { Course } from '../courses';
import { Teacher, Student } from '../users';
import { AcademicYear } from '../academic-years';
import { Room } from '../rooms';


export interface ClassView {
  id: number;
  teacher: Teacher;
  course: Course;
  academicYear: AcademicYear;
  room: Room;
  capacity: number;
}

export interface ClassAdmission {
  id: number;
  teacher: Teacher;
  course: Course;
  academicYear: AcademicYear;
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
