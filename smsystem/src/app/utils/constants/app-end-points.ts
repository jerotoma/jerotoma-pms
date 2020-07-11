import {
  ALL_ROLES,
  ADMINS_AND_EXECUTIVES_ROLES,
  ADMINS_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_STAFF_AND_EXECUTIVES_ROLES,
  ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
} from 'app/models';

export const API_END_POINTS = {
  baseURL: 'https://jerotoma.com',
  dashboard: '/dashboard',
  rootURL: '/api/secured',
  users: '/api/secured/users',
  refreshToken: '/api/auth/refresh-token',
  login: '/api/auth/login',
  register: '/api/auth/register',
  resetPassword: '/api/auth/reset-password',
  logout: '/api/auth/sign-out',
  positions: '/api/secured/positions',
  academicDisciplines: '/api/secured/academic-disciplines',
  rooms: '/api/secured/rooms',
  courses: '/api/secured/courses',
  programs: '/api/secured/programs',
  departments: '/api/secured/departments',
  meetingTimes: '/api/secured/meeting-times',
  workDays: '/api/secured/work-days',
  academicYears: '/api/secured/academic-years',
  classes: '/api/secured/classes',
  studentClasses: '/api/secured/student-classes',
  uploads: '/api/secured/uploads',
  themes: '/api/secured/themes',
  pubThemes: '/api/public/themes',
  systemConfigs: '/api/secured/system-configs',
  pubSystemConfigs: '/api/public/system-configs',
  userPreferences: '/api/secured/user-preferences',
  pubUserPreferences: '/api/public/user-preferences',
  restDashboard: '/api/secured/dashboard',
  attendances: '/api/secured/attendances',
  academicLevels: '/api/secured/academic-levels',
};


export const FRONTEND_ENDPOINTS = {
  dashboard: {
    path: '/dashboard',
    allowedRoles: ALL_ROLES,
  },
  login: {
    path: '/account/login',
    allowedRoles: ALL_ROLES,
  },
  resources: {
    path: '/dashboard/resources',
    allowedRoles: ALL_ROLES,
  },
  users: {
    path: '/dashboard/users',
    allowedRoles: ALL_ROLES,
  },
  staffs: {
    path: '/dashboard/users/staffs',
    allowedRoles: ADMINS_STAFF_AND_EXECUTIVES_ROLES,
  },
  teachers: {
    path: '/dashboard/users/teachers',
    allowedRoles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES,
  },
  parents: {
    path: '/dashboard/users/parents',
    allowedRoles: ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  students: {
    path: '/dashboard/users/students',
    allowedRoles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  attendances: {
    path: '/dashboard/attendances',
    allowedRoles: ALL_ROLES,
  },
  attendanceClasses: {
    path: '/dashboard/attendances/classes',
    allowedRoles: ALL_ROLES,
  },
  attendanceStudents: {
    path: '/dashboard/attendances/students',
    allowedRoles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  attendanceStaffs: {
    path: '/dashboard/attendances/staffs',
    allowedRoles: ADMINS_STAFF_AND_EXECUTIVES_ROLES,
  },
  attendanceTeacher: {
    path: '/dashboard/attendances/teachers',
    allowedRoles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES,
  },
  attendanceParents: {
    path: '/dashboard/attendances/parents',
    allowedRoles: ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  attendanceStatus: {
    path: '/dashboard/attendances/statuses',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
};


export const FRONTEND_ENDPOINT_VALUES = Object.values(FRONTEND_ENDPOINTS);
