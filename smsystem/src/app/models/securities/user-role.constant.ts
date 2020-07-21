export enum USER_ROLE {
  SUPER_ADMIN = 'ROLE_ADMIN',
  ADMIN = 'ROLE_ADMIN',
  TEACHER = 'ROLE_TEACHER',
  PRINCIPAL = 'ROLE_PRINCIPAL',
  MANAGER = 'ROLE_MANAGER',
  DIRECTOR = 'ROLE_DIRECTOR',
  PARENT = 'ROLE_PARENT',
  STUDENT = 'ROLE_STUDENT',
  STAFF = 'ROLE_STAFF',
  LIBRARIAN = 'ROLE_LIBRARIAN',
  OFFICE_ADMINISTRATOR = 'ROLE_OFFICE_ADMINISTRATOR',
  USER = 'ROLE_USER',
  RECEPTIONIST = 'ROLE_RECEPTIONIST',
  ANANYMOUS = 'ROLE_ANANYMOUS',
}

export function convertStringToEnum(enumString: string) {
  let role: USER_ROLE = null;
  switch (enumString) {
    case USER_ROLE.SUPER_ADMIN:
      role = USER_ROLE.SUPER_ADMIN;
      break;
    case USER_ROLE.ADMIN:
      role = USER_ROLE.ADMIN;
      break;
    case USER_ROLE.TEACHER:
      role = USER_ROLE.TEACHER;
      break;
    case USER_ROLE.PRINCIPAL:
      role = USER_ROLE.PRINCIPAL;
      break;
    case USER_ROLE.MANAGER:
      role = USER_ROLE.MANAGER;
      break;
    case USER_ROLE.DIRECTOR:
      role = USER_ROLE.DIRECTOR;
      break;
    case USER_ROLE.RECEPTIONIST:
      role = USER_ROLE.RECEPTIONIST;
      break;
    case USER_ROLE.OFFICE_ADMINISTRATOR:
      role = USER_ROLE.OFFICE_ADMINISTRATOR;
      break;
    case USER_ROLE.PARENT:
      role = USER_ROLE.PARENT;
      break;
    case USER_ROLE.STUDENT:
      role = USER_ROLE.STUDENT;
      break;
    case USER_ROLE.STAFF:
      role = USER_ROLE.STAFF;
      break;
    case USER_ROLE.LIBRARIAN:
      role = USER_ROLE.LIBRARIAN;
      break;
    case USER_ROLE.USER:
      role = USER_ROLE.USER;
      break;
    case USER_ROLE.ANANYMOUS:
      role = USER_ROLE.ANANYMOUS;
      break;
    default:
        role = null;
  }
  return role;
}

export const ALL_ROLES = Object.values(USER_ROLE);
export const STAFF_ONLY_ROLES = [USER_ROLE.STAFF];
export const STAFF_AND_TEACHER_ROLES = [USER_ROLE.TEACHER, USER_ROLE.STAFF];
export const TEACHER_AND_EXECUTIVES_ROLES = [USER_ROLE.TEACHER, USER_ROLE.PRINCIPAL];
export const ADMINS_AND_EXECUTIVES_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN, USER_ROLE.PRINCIPAL];
export const ADMINS_TEACHER_AND_EXECUTIVES_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN, USER_ROLE.TEACHER, USER_ROLE.PRINCIPAL];
export const ADMINS_ONLY_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN];
export const STUDENT_AND_TEACHER_ROLES = [USER_ROLE.STUDENT, USER_ROLE.TEACHER];
export const ADMINS_STUDENT_TEACHER_AND_EXECUTIVES_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN, USER_ROLE.TEACHER, USER_ROLE.STUDENT, USER_ROLE.PRINCIPAL];
export const STUDENT_PARENT_AND_TEACHER_ROLES = [USER_ROLE.STUDENT, USER_ROLE.PARENT, USER_ROLE.TEACHER];
export const ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN, USER_ROLE.TEACHER, USER_ROLE.PRINCIPAL, USER_ROLE.STAFF];
export const PARENT_AND_TEACHER_ROLES = [USER_ROLE.PARENT, USER_ROLE.TEACHER];
export const ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN, USER_ROLE.TEACHER, USER_ROLE.PRINCIPAL, USER_ROLE.PARENT];
export const ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN, USER_ROLE.TEACHER, USER_ROLE.PRINCIPAL, USER_ROLE.PARENT, USER_ROLE.STUDENT];
export const ADMINS_PARENT_AND_EXECUTIVES_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN, USER_ROLE.PRINCIPAL, USER_ROLE.PARENT];
export const ADMINS_STAFF_AND_EXECUTIVES_ROLES = [USER_ROLE.ADMIN, USER_ROLE.SUPER_ADMIN, USER_ROLE.PRINCIPAL, USER_ROLE.STAFF];
