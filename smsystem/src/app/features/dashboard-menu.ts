import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'HOME',
    group: true,
  },
  {
    title: 'Dashboard',
    icon: 'keypad-outline',
    link: '/dashboard',
    home: true,
  },
  {
    title: 'Resources',
    icon: 'monitor-outline',
    link: '/dashboard/resources',
  },
  {
    title: 'FEATURES',
    group: true,
  },
  {
    title: 'Users',
    icon: 'people-outline',
    children: [
      {
        title: 'Teachers',
        link: '/dashboard/users/teachers',
      },
      {
        title: 'Students',
        link: '/dashboard/users/students',
      },
      {
        title: 'Parents',
        link: '/dashboard/users/parents',
      },
      {
        title: 'Staffs',
        link: '/dashboard/users/staffs',
      },
    ],
  },
  {
    title: 'Attendences',
    icon: 'person-done-outline',
    children: [
      {
        title: 'classes',
        link: '/dashboard/attendances/classes',
      },
      {
        title: 'Students',
        link: '/dashboard/attendances/students',
      },
      {
        title: 'Teachers',
        link: '/dashboard/attendances/teachers',
      },
      {
        title: 'Staffs',
        link: '/dashboard/attendances/staffs',
      },
      {
        title: 'Attendance Statuses',
        link: '/dashboard/attendances/statuses',
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
