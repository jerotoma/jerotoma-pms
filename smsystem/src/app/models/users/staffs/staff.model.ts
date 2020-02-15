import { User } from '../user.model';

export interface Staff extends User {
    id?: number;
    title?: string;
  }
