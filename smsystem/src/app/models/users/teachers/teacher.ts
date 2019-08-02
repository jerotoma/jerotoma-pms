import { Person } from '../person';

export interface Teacher extends Person {
  id: number;
  numberOfCourses: Number;
  position: string;
}
