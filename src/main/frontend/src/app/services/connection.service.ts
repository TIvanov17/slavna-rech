import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { FriendConnectionRequest } from '../models/connection.modes';

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
}
