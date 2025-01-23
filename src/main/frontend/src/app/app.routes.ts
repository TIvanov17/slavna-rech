import { Routes } from '@angular/router';
import { ChannelPage } from './features/connection/channel/channel.component';
import { LoginPage } from './features/authentication/login/login.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginPage,
  },
  {
    path: 'channels',
    component: ChannelPage,
  },
];
