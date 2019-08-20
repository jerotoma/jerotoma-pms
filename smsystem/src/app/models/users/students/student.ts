import { Person } from '../person';

export interface Student extends Person {
  id: number;
  numberOfCourses: Number;
  position: string;
  mobilePhone: string;
}
