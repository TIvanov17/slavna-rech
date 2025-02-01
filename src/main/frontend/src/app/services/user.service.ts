import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import {
  UserConnectionResponse,
  UserRequest,
  UserResponse,
} from '../models/user.models';
import { Observable } from 'rxjs';
import { Page, PageFilter } from '../models/page.modes';
import { MemberStatus } from '../enums/member-status.enum';
import { ConnectionResponse } from '../models/connection.modes';
import { MemberResponse } from '../models/member.models';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/users';

  public createUser(userRequest: UserRequest) {
    return this.httpClient.post(this.baseUrl, userRequest);
  }

  public getAllUsers(
    params: HttpParams
  ): Observable<Page<UserConnectionResponse>> {
    return this.httpClient.get<Page<UserConnectionResponse>>(this.baseUrl, {
      params,
    });
  }

  public getFriendsOfUser(
    userId: number,
    params: HttpParams
  ): Observable<Page<UserConnectionResponse>> {
    return this.httpClient.get<Page<UserConnectionResponse>>(
      `${this.baseUrl}/${userId}/connections/friends`,
      {
        params,
      }
    );
  }

  public getChannelsOfUser(
    userId: number,
    params: HttpParams
  ): Observable<Page<ConnectionResponse>> {
    return this.httpClient.get<Page<ConnectionResponse>>(
      `${this.baseUrl}/${userId}/connections/channels`,
      {
        params,
      }
    );
  }

  public getFriendRequestsSendFromUser(
    userId: number,
    params: HttpParams
  ): Observable<Page<UserConnectionResponse>> {
    return this.httpClient.get<Page<UserConnectionResponse>>(
      `${this.baseUrl}/${userId}/connections/friend-requests`,
      {
        params,
      }
    );
  }

  public getFriendInvitesReceivedForUser(
    userId: number,
    params: HttpParams
  ): Observable<Page<UserConnectionResponse>> {
    return this.httpClient.get<Page<UserConnectionResponse>>(
      `${this.baseUrl}/${userId}/connections/friend-invites`,
      {
        params,
      }
    );
  }

  public changeStatusOfMemberFriend(
    userId: number,
    connectionId: number,
    memberStatus: MemberStatus
  ) {
    return this.httpClient.put(
      `${this.baseUrl}/${userId}/connection/${connectionId}/member/${memberStatus}`,
      null
    );
  }

  public getMemberByUserAndConnectionId(
    userId: number,
    connectionId: number
  ): Observable<MemberResponse> {
    return this.httpClient.get<MemberResponse>(
      `${this.baseUrl}/${userId}/connections/${connectionId}`
    );
  }
}
