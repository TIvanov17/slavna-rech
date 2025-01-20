import { Routes } from '@angular/router';
import { ChannelPage } from './features/connection/channel/channel.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'channels',
    pathMatch: 'full',
  },
  {
    path: 'channels',
    component: ChannelPage,
  },
];
