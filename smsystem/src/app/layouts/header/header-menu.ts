import { NbMenuItem } from '@nebular/theme';
import { FRONTEND_ENDPOINTS } from 'app/utils';

export const USER_DROPDOWN_ITEMS: NbMenuItem[] = [
  {
    title: 'Profile',
    link: FRONTEND_ENDPOINTS.profile.path,
    icon: 'person-outline',
   },
   {
    title: 'Preferences',
    link: FRONTEND_ENDPOINTS.preferences.path,
    icon: 'settings-2-outline',
   },
   {
     title: 'Log out',
     link: FRONTEND_ENDPOINTS.logout.path,
     icon: 'log-out-outline',
   },
];
