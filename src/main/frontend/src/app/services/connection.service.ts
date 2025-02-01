import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import {
  ChannelConnectionRequest,
  FriendConnectionRequest,
  UpdateChannelRequest,
} from '../models/connection.modes';

@Injectable({
  providedIn: 'root',
})
export class ConnectionService {
  private httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/connections';

  public sendFriendRequest(friendConnectionRequest: FriendConnectionRequest) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    console.log(friendConnectionRequest);
    return this.httpClient.post<any>(
      `${this.baseUrl}/friend`,
      friendConnectionRequest,
      { headers }
    );
  }

  public createChannel(channelConnectionRequest: ChannelConnectionRequest) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<any>(
      `${this.baseUrl}/channel`,
      channelConnectionRequest,
      { headers }
    );
  }

  public addUserToChannel(connectionId: number, userId: number) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.put<any>(
      `${this.baseUrl}/channels/${connectionId}/user/${userId}`,
      { headers }
    );
  }

  public updateChannel(updateChannelRequest: UpdateChannelRequest) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.put<any>(
      `${this.baseUrl}/channel`,
      updateChannelRequest,
      { headers }
    );
  }
}
