import { Person } from './person.model';


export interface User extends Person {
  username?: string;
  id?: number;
}
