import { Person } from './person.model';
import { Position } from 'app/models/positions';
import { AcademicDiscipline } from 'app/models/academic-disciplines';

export interface User extends Person {
  id?: number;
  userId?: number;
  username?: string;
  userCode?: string;
  userType?: string;
  numberOfCourses?: Number;
  position?: Position;
  academicDiscipline?: AcademicDiscipline;
}
