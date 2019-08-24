import { Person } from '../person.model';

export interface Student extends Person {
  id: number;
  studentNumber: Number;
}
