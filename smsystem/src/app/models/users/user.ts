import { Person } from './person';


export interface User extends Person {
  username?: string;
  id?: number;
}
