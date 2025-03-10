import { Routes } from '@angular/router';
import { ChannelPage } from './features/connection/channel/channel.component';
import { LoginPage } from './features/authentication/login/login.component';
import { RegisterPage } from './features/authentication/registration/registration.component';
import { AuthGuard } from './http/auth.guard';
import { GuestGuard } from './http/non-auth.guard';
import { UserTable } from './features/user/user-table/user-table.component';
import { UserTabs } from './features/user/user-tabs/user-tabs.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginPage,
    canActivate: [GuestGuard],
  },
  {
    path: 'register',
    component: RegisterPage,
    canActivate: [GuestGuard],
  },
  {
    path: 'channels',
    component: ChannelPage,
    canActivate: [AuthGuard],
  },
  {
    path: 'users',
    component: UserTabs,
    canActivate: [AuthGuard],
  },
];
