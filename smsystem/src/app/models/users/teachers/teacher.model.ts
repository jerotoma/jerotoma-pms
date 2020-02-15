
import { User } from '../user.model';

export interface Teacher extends User {
  id: number;
  userId: number;
  teacherCode: string;
}
