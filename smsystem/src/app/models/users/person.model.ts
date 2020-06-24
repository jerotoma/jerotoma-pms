import { Address } from 'app/models/addresses';
export interface Person {
  id?: number;
  userId?: number;
  username?: string;
  password?: string;
  confirmPassword?: string;
  userCode?: string;
  userType?: string;
  firstName?: string;
  lastName?: string;
  middleNames?: string;
  fullName?: string;
  emailAddress?: string;
  phoneNumber?: number;
  gender?: string;
  occupation?: string;
  birthDate?: Date;
  picture?: string;
  address?: Address;
  createdOn?: Date;
  updatedOn?: Date;
}
