
import { Person } from '../person';
import { Position } from 'app/models/positions';
import { AcademicDiscipline } from 'app/models/academic-disciplines';



export interface Teacher extends Person {
  id: number;
  userId: number;
  teacherCode: string;
  numberOfCourses: Number;
  position: Position;
  academicDiscipline: AcademicDiscipline;
}
