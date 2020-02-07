import { Address } from 'app/models/addresses';
export interface Person {
  firstName?: string;
  lastName?: string;
  middleNames?: string;
  fullName?: string;
  phoneNumber?: number;
  emailAddress?: string;
  gender?: string;
  occupation?: string;
  birthDate?: Date;
  picture?: string;
  address?: Address;
  createdOn?: Date;
  updatedOn?: Date;
}
