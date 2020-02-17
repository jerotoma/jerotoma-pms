import { Person } from './person.model';
import { Position } from 'app/models/positions';
import { AcademicDiscipline } from 'app/models/academic-disciplines';

export interface User extends Person {
  id?: number;
  userId?: number;
  username?: string;
  password?: string;
  confirmPassword?: string;
  userCode?: string;
  userType?: string;
  position?: Position;
  academicDiscipline?: AcademicDiscipline;
}
