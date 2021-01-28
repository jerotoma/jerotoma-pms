
import {
  ALL_ROLES,
  USER_ROLE,
  ADMINS_AND_EXECUTIVES_ROLES,
  ADMINS_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_STUDENT_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_STAFF_AND_EXECUTIVES_ROLES,
  ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
  STUDENT_AND_TEACHER_ROLES,
} from 'app/models';

export const API_END_POINTS = {
  baseURL: 'https://jerotoma.com',
  dashboard: '/dashboard',
  rootURL: '/api/secured',
  authUser: '/api/secured/auth/user',
  users: '/api/secured/users',
  refreshToken: '/api/auth/refresh-token',
  login: '/api/auth/login',
  register: '/api/auth/register',
  resetPassword: '/api/auth/reset-password',
  logout: '/api/auth/sign-out',
  positions: '/api/secured/positions',
  academicDisciplines: '/api/secured/academic-disciplines',
  rooms: '/api/secured/rooms',
  streams: '/api/secured/streams',
  courses: '/api/secured/courses',
  programs: '/api/secured/programs',
  studentClasses: '/api/secured/student-classes',
  scoreStandings: '/api/secured/score-standings',
  departments: '/api/secured/departments',
  meetingTimes: '/api/secured/meeting-times',
  workDays: '/api/secured/work-days',
  roles: '/api/secured/roles',
  permissions: '/api/secured/permissions',
  academicYears: '/api/secured/academic-years',
  classes: '/api/secured/classes',
  studentAcademicLevels: '/api/secured/student-academic-levels',
  studentProgresses: '/api/secured/student-progresses',
  uploads: '/api/secured/uploads',
  themes: '/api/secured/themes',
  pubThemes: '/api/public/themes',
  systemConfigs: '/api/secured/system-configs',
  pubSystemConfigs: '/api/public/system-configs',
  userPreferences: '/api/secured/user-preferences',
  pubUserPreferences: '/api/public/user-preferences',
  restDashboard: '/api/secured/dashboard',
  attendances: '/api/secured/attendances',
  attendanceReports: '/api/secured/attendance-reports',
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
    allowedRoles: ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
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
    allowedRoles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  parents: {
    path: '/dashboard/users/parents',
    allowedRoles: ADMINS_STUDENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  students: {
    path: '/dashboard/users/students',
    allowedRoles: ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  attendances: {
    path: '/dashboard/attendances',
    allowedRoles: ALL_ROLES,
  },
  attendanceClasses: {
    path: '/dashboard/attendances/classes',
    allowedRoles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  attendanceStudents: {
    path: '/dashboard/attendances/students',
    allowedRoles: ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
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
  enrollments: {
    path: '/dashboard/enrollments',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  enrollmentStudents: {
    path: '/dashboard/enrollments/student-academic-levels',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  enrollmentClasses: {
    path: '/dashboard/enrollments/classes',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  schedules: {
    path: '/dashboard/schedules',
    allowedRoles: ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
  },
  scheduleMeetingTimes: {
    path: '/dashboard/schedules/meeting-times',
    allowedRoles: ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
  },
  scheduleWorkDays: {
    path: '/dashboard/schedules/work-days',
    allowedRoles: ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
  },
  scheduleTimetables: {
    path: '/dashboard/schedules/timetable',
    allowedRoles: ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
  },
  myschedules: {
    path: '/dashboard/my-schedules',
    allowedRoles: [USER_ROLE.STUDENT],
  },
  myattendances: {
    path: '/dashboard/my-attendances',
    allowedRoles: [USER_ROLE.STUDENT],
  },
  mycourses: {
    path: '/dashboard/my-courses',
    allowedRoles: STUDENT_AND_TEACHER_ROLES,
  },
  media: {
    path: '/dashboard/media',
    allowedRoles: ALL_ROLES,
  },
  mediaStudents: {
    path: '/dashboard/media/students',
    allowedRoles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  },
  mediaTeachers: {
    path: '/dashboard/media/teachers',
    allowedRoles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES,
  },
  mediaStaffs: {
    path: '/dashboard/media/staffs',
    allowedRoles: ADMINS_STAFF_AND_EXECUTIVES_ROLES,
  },
  systemSetup: {
    path: '/dashboard/system-setup',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupAppearances: {
    path: '/dashboard/system-setup/appearances',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupScoreStandings: {
    path: '/dashboard/system-setup/score-standings',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupPositions: {
    path: '/dashboard/system-setup/positions',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupDepartments: {
    path: '/dashboard/system-setup/departments',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupCourses: {
    path: '/dashboard/system-setup/courses',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupRooms: {
    path: '/dashboard/system-setup/rooms',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupStreams: {
    path: '/dashboard/system-setup/streams',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupAcademicYears: {
    path: '/dashboard/system-setup/academic-years',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupAcademicDisciplines: {
    path: '/dashboard/system-setup/academic-disciplines',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupAcademicLevels: {
    path: '/dashboard/system-setup/academic-levels',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  systemSetupPrograms: {
    path: '/dashboard/system-setup/programs',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  securities: {
    path: '/dashboard/securities',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  securitiesRoles: {
    path: '/dashboard/securities/roles',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  securitiesPermissions: {
    path: '/dashboard/securities/permissions',
    allowedRoles: ADMINS_AND_EXECUTIVES_ROLES,
  },
  profile: {
    path: '/dashboard/account/profile',
    allowedRoles: ALL_ROLES,
  },
  preferences: {
    path: '/dashboard/account/preferences',
    allowedRoles: ALL_ROLES,
  },
  logout: {
    path: '/account/logout',
    allowedRoles: ALL_ROLES,
  },
};


export const FRONTEND_ENDPOINT_VALUES = Object.values(FRONTEND_ENDPOINTS);
