import { inject, Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import SockJS from 'sockjs-client/dist/sockjs';
import { Stomp } from '@stomp/stompjs';
import { JWTService } from './jwt.service';
import { GlobalStateService } from './global-state.service';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient: any;
  private messageSubject = new Subject<string>();
  private globalStateService = inject(GlobalStateService);

  constructor() {
    this.connect();
  }

  private connect() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      console.log('Connected to WebSocket server');

      this.stompClient.subscribe('/topic/greeting', (message: any) => {
        this.messageSubject.next(message.body);
      });

      const userId = this.globalStateService.userDetails?.currentUser.id;
      console.log(userId);
      this.stompClient.subscribe(
        `/user/${userId}/topic/private-message`,
        (message: any) => {
          this.messageSubject.next(message.body);
        }
      );
    });
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
