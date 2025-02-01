import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { Sidebar } from '../sidebar/sidebar.component';
import { UserConnectionResponse } from '../../models/user.models';
import { TextInput } from '../text-input/text-input.component';
import { ConnectionResponse } from '../../models/connection.modes';
import { SidebarMenu } from '../../enums/sidebar-menu.enum';

@Component({
  standalone: true,
  selector: 'tabs',
  imports: [NgbNavModule, NgbModule, Sidebar, TextInput],
  templateUrl: './tab.component.html',
  styleUrl: './tab.component.css',
})
export class Tabs {
  SidebarMenu = SidebarMenu;
  active: SidebarMenu = SidebarMenu.FRIENDS;
  @Input() friends: UserConnectionResponse[] = [];
  @Input() channels: ConnectionResponse[] = [];

  @Output() userSelected = new EventEmitter<UserConnectionResponse>();
  @Output() channelSelected = new EventEmitter<ConnectionResponse>();
  @Output() fetchFriends = new EventEmitter<void>();
  @Output() fetchChannels = new EventEmitter<void>();
  @Output() selectTab = new EventEmitter<SidebarMenu>();

  onUserSelected(user: UserConnectionResponse): void {
    this.userSelected.emit(user);
  }

  onChannelSelected(channel: ConnectionResponse): void {
    this.channelSelected.emit(channel);
  }

  onChannelCreated(): void {
    this.fetchChannels.emit();
  }

  onTabChange(tab: SidebarMenu): void {
    this.selectTab.emit(tab);
  }
}
