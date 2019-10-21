import { NbMenuItem } from '@nebular/theme';

export const USER_DROPDOWN_ITEMS: NbMenuItem[] = [
  {
    title: 'Profile',
    link: '/dashboard/account/profile',
    icon: 'person-outline',
   },
   {
    title: 'My Preferences',
    link: '/dashboard/account/preferences',
    icon: 'settings-2-outline',
   },
   {
     title: 'Log out',
     link: '/account/logout',
     icon: 'log-out-outline',
   },
];
