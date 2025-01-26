import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../../../services/websocket.service';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { UserResponse } from '../../../models/user.models';
import { Tabs } from '../../../components/tabs/tab.component';
import { AuthenticationService } from '../../../services/authentication.service';
import { GlobalStateService } from '../../../services/global-state.service';
import { MessageRequest } from '../../../models/message.model';
import { HttpParams } from '@angular/common/http';

@Component({
  standalone: true,
  templateUrl: './channel.component.html',
  styleUrl: './channel.component.css',
  imports: [FormsModule, Tabs],
})
export class ChannelPage implements OnInit, OnDestroy {
  message: string = '';
  messages: string[] = [];

  users: UserResponse[] = [];
  selectedUserId: number | null = null;

  private subscription: Subscription | undefined;
  private webSocketService = inject(WebSocketService);
  private userService = inject(UserService);
  private globalStateService = inject(GlobalStateService);

  ngOnInit() {
    this.subscription = this.webSocketService.getMessages().subscribe((msg) => {
      this.messages.push(msg);
    });
    const params = new HttpParams();

    this.userService.getAllUsers(params).subscribe((data) => {
      console.log(data.content);
      this.users = data.content;
    });
  }

  handleUserSelection(userId: number): void {
    this.selectedUserId = userId;
  }

  sendMessage() {
    this.webSocketService.sendMessage('/app/hello', this.message);
    this.message = '';
  }

  sendPrivateMessage() {
    this.webSocketService.sendMessage(`/app/private-message/2`, this.message);
    this.message = '';
  }

  sendMessageWithPayload() {
    const payload: MessageRequest = {
      senderId: this.globalStateService.userDetails?.currentUser.id || 1,
      receiverId: 2,
      content: this.message,
    };
    this.webSocketService.sendMessageWithPayload(
      `/app/private-message`,
      payload
    );
  }
  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.webSocketService.disconnect();
  }
}
