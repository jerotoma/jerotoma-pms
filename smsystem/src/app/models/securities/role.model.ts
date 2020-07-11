import { USER_ROLE } from './user-role.constant';

export interface Role {
  id: number;
  name: USER_ROLE;
  displayName: string;
  createdOn: Date;
  updatedOn: Date;
}
