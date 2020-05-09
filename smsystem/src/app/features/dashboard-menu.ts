import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'HOME',
    group: true,
  },
  {
    title: 'Dashboard',
    icon: 'home-outline',
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
    title: 'Admission',
    icon: 'book-open-outline',
    children: [
      {
        title: 'Students',
        link: '/dashboard/admissions/students',
      },
      {
        title: 'Teachers & Classes',
        link: '/dashboard/admissions/classes',
      },
    ],
  },
  {
    title: 'SCHOOL SCHEDULES',
    group: true,
  },
  {
    title: 'Students',
    icon: 'calendar-outline',
    link: '/dashboard/ui-features',
    children: [
      {
        title: 'Grid',
        link: '/dashboard/ui-features/grid',
      },
      {
        title: 'Icons',
        link: '/dashboard/ui-features/icons',
      },
      {
        title: 'Typography',
        link: '/dashboard/ui-features/typography',
      },
      {
        title: 'Animated Searches',
        link: '/dashboard/ui-features/search-fields',
      },
    ],
  },
  {
    title: 'Teachers',
    icon: 'calendar-outline',
    children: [
      {
        title: 'Calendar',
        link: '/dashboard/extra-components/calendar',
      },
      {
        title: 'Progress Bar',
        link: '/dashboard/extra-components/progress-bar',
      },
      {
        title: 'Spinner',
        link: '/dashboard/extra-components/spinner',
      },
      {
        title: 'Alert',
        link: '/dashboard/extra-components/alert',
      },
      {
        title: 'Calendar Kit',
        link: '/dashboard/extra-components/calendar-kit',
      },
      {
        title: 'Chat',
        link: '/dashboard/extra-components/chat',
      },
    ],
  },
  {
    title: 'SECURITY',
    group: true,
  },
  {
    title: 'Auth',
    icon: 'lock-outline',
    children: [
      {
        title: 'Login',
        link: '/account/login',
      },
      {
        title: 'Register',
        link: '/account/register',
      },
      {
        title: 'Request Password',
        link: '/account/request-password',
      },
      {
        title: 'Reset Password',
        link: '/account/reset-password',
      },
    ],
  },
  {
    title: 'Roles & Permission',
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
  {
    title: 'SETTINGS',
    group: true,
  },
  {
    title: 'System Setup',
    icon: 'shuffle-2-outline',
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
];
