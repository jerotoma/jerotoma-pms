import { NbMenuItem } from '@nebular/theme';

export const USER_DROPDOWN_ITEMS: NbMenuItem[] = [
  {
    title: 'Profile',
    link: '/dashboard/account/profile',
    icon: 'person-outline',
   },
   {
    title: 'Settings',
    link: '/dashboard/account/settings',
    icon: 'settings-2-outline',
   },
   {
     title: 'Log out',
     link: '/account/logout',
     icon: 'log-out-outline',
   },
];

export const THEMES: any[] = [
  {
    value: 'default',
    name: 'Light',
  },
  {
    value: 'dark',
    name: 'Dark',
  },
  {
    value: 'cosmic',
    name: 'Cosmic',
  },
  {
    value: 'corporate',
    name: 'Corporate',
  },
];
