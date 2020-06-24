import { Person } from './person.model';
import { Position } from 'app/models/positions';
import { AcademicDiscipline } from 'app/models/academic-disciplines';

export interface User extends Person {
  position?: Position;
  academicDiscipline?: AcademicDiscipline;
}
