import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../../../services/websocket.service';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { UserConnectionResponse } from '../../../models/user.models';
import { Tabs } from '../../../components/tabs/tab.component';
import { GlobalStateService } from '../../../services/global-state.service';
import { MessageRequest } from '../../../models/message.model';
import { HttpParams } from '@angular/common/http';
import { PageFilter } from '../../../models/page.modes';
import { MessageService } from '../../../services/message.service';
import { ConnectionResponse } from '../../../models/connection.modes';
import { ChannelSettings } from './channel-settings/channel-settings.component';

@Component({
  standalone: true,
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.css',
  imports: [FormsModule, Tabs, ChannelSettings],
})
export class ChannelPage implements OnInit, OnDestroy {
  message: string = '';
  messages: string[] = [];

  users: UserConnectionResponse[] = [];
  channels: ConnectionResponse[] = [];

  private subscription: Subscription | undefined;
  private webSocketService = inject(WebSocketService);
  private userService = inject(UserService);
  private globalStateService = inject(GlobalStateService);
  private messageService = inject(MessageService);

  selectedUser: UserConnectionResponse | null = null;
  selectedChannel: ConnectionResponse | null = null;

  historyMessages: string[] = [];

  @Input() userSearchCriteria: PageFilter = {
    page: 1,
    pageSize: 5,
    searchKeyword: '',
    sortColumn: 'username',
    sortDirection: 'asc',
  };

  ngOnInit() {
    this.subscription = this.webSocketService.getMessages().subscribe((msg) => {
      this.messages.push(msg);
    });
    this.fetchFriends();
    this.fetchChannels();
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
    this.subscription = this.webSocketService.getMessages().subscribe((msg) => {
      this.messages.push(msg);
    });

    this.messageService
      .getMessageForConnection(user.connectionId)
      .subscribe((data) => {
        const contentString = data.map((message) => message.content);
        this.historyMessages = contentString;
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
    this.subscription = this.webSocketService.getMessages().subscribe((msg) => {
      this.messages.push(msg);
    });

    this.messageService
      .getMessageForConnection(channel.id)
      .subscribe((data) => {
        const contentString = data.map((message) => message.content);
        this.historyMessages = contentString;
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

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.webSocketService.disconnect();
  }
}
