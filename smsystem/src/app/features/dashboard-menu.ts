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
    title: 'School Resources',
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
        title: 'Teachers & Classes',
        link: '/dashboard/admissions/classes',
      },
      {
        title: 'Students',
        link: '/dashboard/admissions/students',
      },
    ],
  },
  {
    title: 'SCHOOL SCHEDULES',
    group: true,
  },
  {
    title: 'Students',
    icon: 'keypad-outline',
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
    icon: 'message-circle-outline',
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
    icon: 'browser-outline',
    children: [
      {
        title: 'Dialog',
        link: '/dashboard/modal-overlays/dialog',
      },
      {
        title: 'Window',
        link: '/dashboard/modal-overlays/window',
      },
      {
        title: 'Popover',
        link: '/dashboard/modal-overlays/popover',
      },
      {
        title: 'Toastr',
        link: '/dashboard/modal-overlays/toastr',
      },
      {
        title: 'Tooltip',
        link: '/dashboard/modal-overlays/tooltip',
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
        title: 'Class Rooms',
        link: '/dashboard/system-setup/class-rooms',
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
