import { USER_TYPE } from 'app/utils';
import { USER_ROLE } from 'app/models/securities';

export interface Auth {
  userId: number;
  fullName: string;
  avatar: string;
  avatarId: number;
  roles: USER_ROLE[];
  userType: USER_TYPE;
}
