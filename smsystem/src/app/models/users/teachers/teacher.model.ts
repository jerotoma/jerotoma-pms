
import { User, Department } from 'app/models';

export interface Teacher extends User {
  id: number;
  userId: number;
  teacherCode: string;
  department?: Department;
}
