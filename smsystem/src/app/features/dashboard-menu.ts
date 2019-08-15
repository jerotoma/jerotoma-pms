import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
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
        title: 'Other Staffs',
        link: '/dashboard/users/other-staffs',
      },
    ],
  },
  {
    title: 'Admission',
    icon: 'book-open-outline',
    children: [
      {
        title: 'Form Inputs',
        link: '/dashboard/forms/inputs',
      },
      {
        title: 'Form Layouts',
        link: '/dashboard/forms/layouts',
      },
      {
        title: 'Buttons',
        link: '/dashboard/forms/buttons',
      },
      {
        title: 'Datepicker',
        link: '/dashboard/forms/datepicker',
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
        title: 'Positions',
        link: '/dashboard/system-setup/positions',
      },
      {
        title: 'Academic Disciplines',
        link: '/dashboard/system-setup/academic-disciplines',
      },
    ],
  },
  {
    title: 'UI Features',
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
    title: 'Modal & Overlays',
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
    title: 'Schedules',
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
    title: 'School Maps',
    icon: 'map-outline',
    children: [
      {
        title: 'Google Maps',
        link: '/dashboard/maps/gmaps',
      },
      {
        title: 'Leaflet Maps',
        link: '/dashboard/maps/leaflet',
      },
      {
        title: 'Bubble Maps',
        link: '/dashboard/maps/bubble',
      },
      {
        title: 'Search Maps',
        link: '/dashboard/maps/searchmap',
      },
    ],
  },
  {
    title: 'Charts',
    icon: 'pie-chart-outline',
    children: [
      {
        title: 'Echarts',
        link: '/dashboard/charts/echarts',
      },
      {
        title: 'Charts.js',
        link: '/dashboard/charts/chartjs',
      },
      {
        title: 'D3',
        link: '/dashboard/charts/d3',
      },
    ],
  },
  {
    title: 'Editors',
    icon: 'text-outline',
    children: [
      {
        title: 'TinyMCE',
        link: '/dashboard/editors/tinymce',
      },
      {
        title: 'CKEditor',
        link: '/dashboard/editors/ckeditor',
      },
    ],
  },
  {
    title: 'Tables & Data',
    icon: 'grid-outline',
    children: [
      {
        title: 'Smart Table',
        link: '/dashboard/tables/smart-table',
      },
      {
        title: 'Tree Grid',
        link: '/dashboard/tables/tree-grid',
      },
    ],
  },
  {
    title: 'Miscellaneous',
    icon: 'shuffle-2-outline',
    children: [
      {
        title: '404',
        link: '/dashboard/miscellaneous/404',
      },
    ],
  },
];
