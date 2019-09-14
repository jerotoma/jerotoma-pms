import { Course } from '../courses';
import { Teacher, Student } from '../users';
import { AcademicYear } from '../academic-years';
import { ClassRoom } from '../class-rooms';


export interface JClassView {
  id: number;
  teacher: Teacher;
  course: Course;
  academicYear: AcademicYear;
  classRoom: ClassRoom;
  capacity: number;
}

export interface JClassAdmission {
  id: number;
  teacher: Teacher;
  course: Course;
  academicYear: AcademicYear;
  classRoom: ClassRoom;
  capacity: number;
}

export interface StudentClass {
  id: number;
  student: Student;
  academicYear: AcademicYear;
  jClasses: JClassView[];
  numberOfCourse: number;
}


export interface StudentClassAdmission {
  id: number;
  studentId: number;
  classIds: number[];
  academicYearId: number;
}
