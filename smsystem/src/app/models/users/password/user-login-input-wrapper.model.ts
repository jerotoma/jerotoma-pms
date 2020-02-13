import { UserLoginInput } from './user-login-input.model';

export interface UserLoginInputWrapper {
  userLoginInput: UserLoginInput;
  canUserLogin: boolean;
  isValid: boolean;

}
