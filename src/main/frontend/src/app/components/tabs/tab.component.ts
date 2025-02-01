import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { Sidebar } from '../sidebar/sidebar.component';
import { UserConnectionResponse, UserResponse } from '../../models/user.models';
import { TextInput } from '../text-input/text-input.component';
import {
  ChannelConnectionRequest,
  ConnectionResponse,
} from '../../models/connection.modes';

@Component({
  standalone: true,
  selector: 'tabs',
  imports: [NgbNavModule, NgbModule, Sidebar, TextInput],
  templateUrl: './tab.component.html',
  styleUrl: './tab.component.css',
})
export class Tabs {
  active = 1;
  @Input() friends: UserConnectionResponse[] = [];
  @Input() channels: ConnectionResponse[] = [];

  @Output() userSelected = new EventEmitter<UserConnectionResponse>();
  @Output() channelSelected = new EventEmitter<ConnectionResponse>();
  @Output() fetchFriends = new EventEmitter<void>();
  @Output() fetchChannels = new EventEmitter<void>();

  onUserSelected(user: UserConnectionResponse): void {
    this.userSelected.emit(user);
  }

  onChannelSelected(channel: ConnectionResponse): void {
    this.channelSelected.emit(channel);
  }

  onTabChange(tab: string): void {
    if (tab === 'friends') {
      this.fetchFriends.emit();
    } else if (tab === 'channels') {
      this.fetchChannels.emit();
    }
  }
}
