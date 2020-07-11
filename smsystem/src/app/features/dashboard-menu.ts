import { NbMenuItem } from '@nebular/theme';
import { FRONTEND_ENDPOINTS } from 'app/utils';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'HOME',
    group: true,
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
    children: [
      {
        title: 'Students',
        link: '/dashboard/admissions/students',
      },
      {
        title: 'Classes',
        link: '/dashboard/admissions/classes',
      },
    ],
  },
  {
    title: 'Schedules',
    icon: 'calendar-outline',
    children: [
      {
        title: 'Meeting Times',
        link: '/dashboard/schedules/meeting-times',
      },
      {
        title: 'Work Days',
        link: '/dashboard/schedules/work-days',
      },
      {
        title: 'Timetable',
        link: '/dashboard/schedules/timetable',
      },
    ],
  },
  {
    title: 'Media',
    icon: 'image-outline',
    children: [
      {
        title: 'All',
        link: '/dashboard/media',
      },
      {
        title: 'Students',
        link: '/dashboard/media/students',
      },
      {
        title: 'Teachers',
        link: '/dashboard/media/teachers',
      },
      {
        title: 'Staffs',
        link: '/dashboard/media/staffs',
      },
    ],
  },
  {
    title: 'SETTINGS',
    group: true,
  },
  {
    title: 'System Setup',
    icon: 'shuffle-outline',
    link: '/dashboard/system-setup',
    children: [
      {
        title: 'Appearances',
        link: '/dashboard/system-setup/appearances',
      },
      {
        title: 'Positions',
        link: '/dashboard/system-setup/positions',
      },
      {
        title: 'Courses',
        link: '/dashboard/system-setup/courses',
      },
      {
        title: 'Departments',
        link: '/dashboard/system-setup/departments',
      },
      {
        title: 'Rooms',
        link: '/dashboard/system-setup/rooms',
      },
      {
        title: 'Academic Years',
        link: '/dashboard/system-setup/academic-years',
      },
      {
        title: 'Academic Disciplines',
        link: '/dashboard/system-setup/academic-disciplines',
      },
      {
        title: 'Academic Levels',
        link: '/dashboard/system-setup/academic-levels',
      },
      {
        title: 'Programs',
        link: '/dashboard/system-setup/programs',
      },
    ],
  },
  {
    title: 'Security Setup',
    icon: 'shield-outline',
    children: [
      {
        title: 'Roles',
        link: '/dashboard/securities/roles',
      },
      {
        title: 'Permissions',
        link: '/dashboard/securities/permissions',
      },
    ],
  },
];
