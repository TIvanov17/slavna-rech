import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../../../services/websocket.service';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { UserConnectionResponse } from '../../../models/user.models';
import { Tabs } from '../../../components/tabs/tab.component';
import { GlobalStateService } from '../../../services/global-state.service';
import { MessageDTO, MessageRequest } from '../../../models/message.model';
import { HttpParams } from '@angular/common/http';
import { PageFilter } from '../../../models/page.modes';
import { MessageService } from '../../../services/message.service';
import { ConnectionResponse } from '../../../models/connection.modes';
import { ChannelSettings } from './channel-settings/channel-settings.component';
import { SidebarMenu } from '../../../enums/sidebar-menu.enum';
import { NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.css',
  imports: [
    FormsModule,
    Tabs,
    ChannelSettings,
    NgbAccordionModule,
    CommonModule,
  ],
})
export class ChannelPage implements OnInit, OnDestroy {
  SidebarMenu = SidebarMenu;
  message: string = '';
  messages: MessageDTO[] = [];

  users: UserConnectionResponse[] = [];
  channels: ConnectionResponse[] = [];

  selectedTab: SidebarMenu | null = SidebarMenu.FRIENDS;

  selectedUser: UserConnectionResponse | null = null;
  selectedChannel: ConnectionResponse | null = null;

  private subscription: Subscription | undefined;
  private webSocketService = inject(WebSocketService);
  private userService = inject(UserService);
  private globalStateService = inject(GlobalStateService);
  private messageService = inject(MessageService);

  historyMessages: MessageDTO[] = [];
  userId = this.globalStateService.userDetails?.currentUser.id;

  @Input() userSearchCriteria: PageFilter = {
    page: 1,
    pageSize: 5,
    searchKeyword: '',
    sortColumn: 'username',
    sortDirection: 'asc',
  };

  ngOnInit() {
    this.subscription = this.webSocketService
      .getMessages()
      .subscribe((data) => {
        const messageDTO: MessageDTO = JSON.parse(data);
        this.messages.push(messageDTO);
      });
    this.fetchFriends();
    this.fetchChannels();
  }

  onTabChange(tab: SidebarMenu): void {
    this.selectedTab = tab;
    if (tab === SidebarMenu.FRIENDS) {
      this.fetchFriends();
    } else if (tab === SidebarMenu.CHANNELS) {
      this.fetchChannels();
    }
  }

  fetchFriends() {
    const params = this.getHttpParams(this.userSearchCriteria);
    const userId = this.globalStateService.userDetails?.currentUser.id;

    if (userId) {
      this.userService.getFriendsOfUser(userId, params).subscribe((data) => {
        this.users = data.content;
        if (this.users.length > 0) {
          this.setSelectedUser(this.users[0]);
        }
      });
    }
  }

  fetchChannels() {
    const params = this.getHttpParams(this.userSearchCriteria);
    const userId = this.globalStateService.userDetails?.currentUser.id;

    if (userId) {
      this.userService.getChannelsOfUser(userId, params).subscribe((data) => {
        this.channels = data.content;
        if (this.channels.length > 0) {
          this.setSelectedChannel(this.channels[0]);
        }
      });
    }
  }

  private getHttpParams(criteria: PageFilter) {
    return new HttpParams()
      .set('page', criteria.page)
      .set('pageSize', criteria.pageSize)
      .set('searchKeyword', criteria.searchKeyword)
      .set('sortColumn', criteria.sortColumn)
      .set('sortDirection', criteria.sortDirection);
  }

  setSelectedUser(user: UserConnectionResponse): void {
    // if (this.selectedUser && this.selectedUser.id === user.id) {
    //   return;
    // }
    this.selectedUser = user;
    this.selectedChannel = null;
    this.messages = [];
    this.historyMessages = [];
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.webSocketService.disconnect();

    this.webSocketService.connect(user.connectionId);
    this.subscription = this.webSocketService
      .getMessages()
      .subscribe((data) => {
        const messageDTO: MessageDTO = JSON.parse(data);
        this.messages.push(messageDTO);
      });

    this.messageService
      .getMessageForConnection(user.connectionId)
      .subscribe((data) => {
        // const contentString = data.map((message) => message.content);
        this.historyMessages = data;
      });
  }

  setSelectedChannel(channel: ConnectionResponse): void {
    this.selectedChannel = channel;
    this.selectedUser = null;
    this.messages = [];
    this.historyMessages = [];

    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.webSocketService.disconnect();
    this.webSocketService.connect(channel.id);
    this.subscription = this.webSocketService
      .getMessages()
      .subscribe((data) => {
        const messageDTO: MessageDTO = JSON.parse(data);
        this.messages.push(messageDTO);
      });

    this.messageService
      .getMessageForConnection(channel.id)
      .subscribe((data) => {
        // const contentString = data.map((message) => message.content);
        this.historyMessages = data;
      });
  }

  sendMessageWithPayload() {
    const connectionId =
      this.selectedUser?.connectionId || this.selectedChannel?.id;

    if (connectionId) {
      const payload: MessageRequest = {
        senderId: this.globalStateService.userDetails?.currentUser.id || 1,
        connectionId: connectionId,
        content: this.message,
      };
      this.webSocketService.sendMessageWithPayload(
        `/app/private-message`,
        payload
      );
      this.message = '';
    }
  }

  onKeyDown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      if (event.shiftKey) {
        return;
      }
      event.preventDefault();
      this.sendMessageWithPayload();
    }
  }

  formatMessage(content: string): string {
    return content.replace(/\n/g, '<br/>');
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.webSocketService.disconnect();
  }
}
