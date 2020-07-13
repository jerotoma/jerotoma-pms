import { NbMenuItem } from '@nebular/theme';
import { FRONTEND_ENDPOINTS } from 'app/utils';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'HOME',
    group: true,
    hidden: false,
  },
  {
    title: 'Dashboard',
    icon: 'keypad-outline',
    link: FRONTEND_ENDPOINTS.dashboard.path,
    home: true,
  },
  {
    title: 'Resources',
    icon: 'monitor-outline',
    link: FRONTEND_ENDPOINTS.resources.path,
  },
  {
    title: 'FEATURES',
    group: true,
    hidden: false,
  },
  {
    title: 'Users',
    icon: 'people-outline',
    link: FRONTEND_ENDPOINTS.users.path,
    children: [
      {
        title: 'Teachers',
        link: FRONTEND_ENDPOINTS.teachers.path,
      },
      {
        title: 'Students',
        link: FRONTEND_ENDPOINTS.students.path,
      },
      {
        title: 'Parents',
        link: FRONTEND_ENDPOINTS.parents.path,
      },
      {
        title: 'Staffs',
        link: FRONTEND_ENDPOINTS.staffs.path,
      },
    ],
  },
  {
    title: 'Attendences',
    icon: 'person-done-outline',
    link: FRONTEND_ENDPOINTS.attendances.path,
    children: [
      {
        title: 'classes',
        link: FRONTEND_ENDPOINTS.attendanceClasses.path,
      },
      {
        title: 'Students',
        link: FRONTEND_ENDPOINTS.attendanceStudents.path,
      },
      {
        title: 'Teachers',
        link: FRONTEND_ENDPOINTS.attendanceTeacher.path,
      },
      {
        title: 'Staffs',
        link: FRONTEND_ENDPOINTS.attendanceStaffs.path,
      },
      {
        title: 'Attendance Statuses',
        link: FRONTEND_ENDPOINTS.attendanceStatus.path,
      },
    ],
  },
  {
    title: 'Admission',
    icon: 'book-open-outline',
    link: FRONTEND_ENDPOINTS.admission.path,
    children: [
      {
        title: 'Students',
        link: FRONTEND_ENDPOINTS.admissionStudents.path,
      },
      {
        title: 'Classes',
        link: FRONTEND_ENDPOINTS.admissionClasses.path,
      },
    ],
  },
  {
    title: 'Schedules',
    icon: 'calendar-outline',
    link: FRONTEND_ENDPOINTS.schedules.path,
    children: [
      {
        title: 'Meeting Times',
        link: FRONTEND_ENDPOINTS.scheduleMeetingTimes.path,
      },
      {
        title: 'Work Days',
        link: FRONTEND_ENDPOINTS.scheduleWorkDays.path,
      },
      {
        title: 'Timetable',
        link: FRONTEND_ENDPOINTS.scheduleTimetables.path,
      },
    ],
  },
  {
    title: 'Media',
    icon: 'image-outline',
    link: FRONTEND_ENDPOINTS.media.path,
    children: [
      {
        title: 'All',
        link: FRONTEND_ENDPOINTS.media.path,
      },
      {
        title: 'Students',
        link: FRONTEND_ENDPOINTS.mediaStudents.path,
      },
      {
        title: 'Teachers',
        link: FRONTEND_ENDPOINTS.mediaTeachers.path,
      },
      {
        title: 'Staffs',
        link: FRONTEND_ENDPOINTS.mediaStaffs.path,
      },
    ],
  },
  {
    title: 'SETTINGS',
    group: true,
    hidden: false,
  },
  {
    title: 'System Setup',
    icon: 'shuffle-outline',
    link: FRONTEND_ENDPOINTS.systemSetup.path,
    children: [
      {
        title: 'Appearances',
        link: FRONTEND_ENDPOINTS.systemSetupAppearances.path,
      },
      {
        title: 'Positions',
        link: FRONTEND_ENDPOINTS.systemSetupPositions.path,
      },
      {
        title: 'Courses',
        link: FRONTEND_ENDPOINTS.systemSetupAcademicYears.path,
      },
      {
        title: 'Departments',
        link: FRONTEND_ENDPOINTS.systemSetupDepartments.path,
      },
      {
        title: 'Rooms',
        link: FRONTEND_ENDPOINTS.systemSetupRooms.path,
      },
      {
        title: 'Academic Years',
        link: FRONTEND_ENDPOINTS.systemSetupAcademicYears.path,
      },
      {
        title: 'Academic Disciplines',
        link: FRONTEND_ENDPOINTS.systemSetupAcademicDisciplines.path,
      },
      {
        title: 'Academic Levels',
        link: FRONTEND_ENDPOINTS.systemSetupAcademicLevels.path,
      },
      {
        title: 'Programs',
        link: FRONTEND_ENDPOINTS.systemSetupPrograms.path,
      },
    ],
  },
  {
    title: 'Security Setup',
    icon: 'shield-outline',
    link: FRONTEND_ENDPOINTS.securities.path,
    children: [
      {
        title: 'Roles',
        link: FRONTEND_ENDPOINTS.securitiesRoles.path,
      },
      {
        title: 'Permissions',
        link: FRONTEND_ENDPOINTS.securitiesPermissions.path,
      },
    ],
  },
];
