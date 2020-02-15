import { Position } from 'app/models';
import { User } from '../user.model';

export interface Staff extends User {
    title?: string;
  }
