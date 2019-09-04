import { Course } from '../courses';
import { Teacher } from '../users';
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
  teacherId: number;
  courseId: number;
  academicYearId: number;
  classRoomId: number;
  capacity: number;
}
