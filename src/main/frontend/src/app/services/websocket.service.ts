import { inject, Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import SockJS from 'sockjs-client/dist/sockjs';
import { Stomp } from '@stomp/stompjs';
import { JWTService } from './jwt.service';
import { GlobalStateService } from './global-state.service';
import { UserService } from './user.service';
import { HttpParams } from '@angular/common/http';
import { PageFilter } from '../models/page.modes';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient: any;
  private messageSubject = new Subject<string>();
  private globalStateService = inject(GlobalStateService);
  private userService = inject(UserService);

  constructor() {
    this.connect();
  }

  connect(connectionId?: number) {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      console.log('Connected to WebSocket server');

      const userId = this.globalStateService.userDetails?.currentUser.id;

      if (userId) {
        this.stompClient.subscribe(
          `/user/${connectionId}/topic/private-message`,
          (message: any) => {
            this.messageSubject.next(message.body);
          }
        );
        // this.userService
        //   .getFriendsOfUser(userId, this.getHttpParams())
        //   .subscribe((data) => {
        //     for (let friend of data.content) {
        //       this.stompClient.subscribe(
        //         `/user/${friend.connectionId}/topic/private-message`,
        //         (message: any) => {
        //           this.messageSubject.next(message.body);
        //         }
        //       );
        //     }
        //   });
      }
    });
  }

  private getHttpParams() {
    return new HttpParams().set('page', 1).set('pageSize', 1000);
  }

  sendMessage(destination: string, message: string): void {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(destination, {}, message);
    }
  }

  sendMessageWithPayload(destination: string, payload: any): void {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(destination, {}, JSON.stringify(payload));
    }
  }

  getMessages(): Observable<string> {
    return this.messageSubject.asObservable();
  }

  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect(() => {
        console.log('Disconnected from WebSocket server');
      });
    }
  }
}
