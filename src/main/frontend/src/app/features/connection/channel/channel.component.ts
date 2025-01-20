import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from '../../../services/websocket.service';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { UserResponse } from '../../../models/user.models';
import { Tabs } from '../../../components/tabs/tab.component';

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

  ngOnInit() {
    this.subscription = this.webSocketService.getMessages().subscribe((msg) => {
      this.messages.push(msg);
    });
    this.userService.getAllUsers().subscribe((data) => {
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

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.webSocketService.disconnect();
  }
}
