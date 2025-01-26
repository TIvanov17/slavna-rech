import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { UserTable } from '../user-table/user-table.component';
import { UserResponse } from '../../../models/user.models';
import { HttpParams } from '@angular/common/http';
import { PageFilter } from '../../../models/page.modes';
import { UserService } from '../../../services/user.service';
import { GlobalStateService } from '../../../services/global-state.service';
import { ConnectionService } from '../../../services/connection.service';
import { IconComponent } from '../../../components/icon/icon.component';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'user-tabs',
  imports: [NgbNavModule, NgbModule, UserTable, IconComponent, CommonModule],
  templateUrl: './user-tabs.component.html',
  styleUrl: './user-tabs.component.css',
})
export class UserTabs {
  activeTab = 'users';

  users$: UserResponse[] = [];
  total$: number = 0;

  friends$: UserResponse[] = [];
  totalFriends$ = 0;

  requests$: UserResponse[] = [];
  totalRequests$ = 0;

  invites$: UserResponse[] = [];
  totalInvites$ = 0;

  searchCriteria: PageFilter = {
    page: 1,
    pageSize: 2,
    searchKeyword: '',
    sortColumn: 'username',
    sortDirection: 'asc',
  };
  private userService = inject(UserService);
  private globalStateService = inject(GlobalStateService);
  private connectionService = inject(ConnectionService);

  fetchAllUsers = (criteria: PageFilter): void => {
    const params = this.getHttpParams(criteria);
    this.userService.getAllUsers(params).subscribe((data) => {
      this.users$ = data.content;
      this.total$ = data.totalElements;
    });
  };

  fetchFriends = (criteria: PageFilter): void => {
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    if (senderId) {
      const params = this.getHttpParams(criteria);
      this.userService.getFriendsOfUser(senderId, params).subscribe((data) => {
        this.friends$ = data.content;
        this.totalFriends$ = data.totalElements;
      });
    }
  };

  fetchFriendRequestsSendFromUser = (criteria: PageFilter): void => {
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    if (senderId) {
      const params = this.getHttpParams(criteria);
      this.userService
        .getFriendRequestsSendFromUser(senderId, params)
        .subscribe((data) => {
          this.requests$ = data.content;
          this.totalFriends$ = data.totalElements;
        });
    }
  };

  fetchFriendInvitesReceivedForUser = (criteria: PageFilter): void => {
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    if (senderId) {
      const params = this.getHttpParams(criteria);
      this.userService
        .getFriendInvitesReceivedForUser(senderId, params)
        .subscribe((data) => {
          this.requests$ = data.content;
          this.totalFriends$ = data.totalElements;
        });
    }
  };

  private getHttpParams(criteria: PageFilter) {
    return new HttpParams()
      .set('page', criteria.page)
      .set('pageSize', criteria.pageSize)
      .set('searchKeyword', criteria.searchKeyword)
      .set('sortColumn', criteria.sortColumn)
      .set('sortDirection', criteria.sortDirection);
  }

  addFriend = (userId: number): void => {
    console.log(userId);
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    if (senderId) {
      this.connectionService
        .sendFriendRequest({
          senderId: senderId,
          receiverId: userId,
        })
        .subscribe((data) => {
          console.log('Friend request sent successfully');
        });
    }
  };

  removeFriend = (userId: number): void => {
    console.log(userId);
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    if (senderId) {
      this.connectionService
        .sendFriendRequest({
          senderId: senderId,
          receiverId: userId,
        })
        .subscribe((data) => {
          console.log('Friend request sent successfully');
        });
    }
  };

  changeStatusOfFriendRequest = (userId: number): void => {
    console.log(userId);
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    if (senderId) {
      this.connectionService
        .sendFriendRequest({
          senderId: senderId,
          receiverId: userId,
        })
        .subscribe((data) => {
          console.log('Friend request sent successfully');
        });
    }
  };

  onTabChange(tab: string): void {
    this.activeTab = tab;

    if (tab === 'users') {
      this.fetchAllUsers(this.searchCriteria);
    } else if (tab === 'friends') {
      this.fetchFriends(this.searchCriteria);
    } else if (tab === 'requests') {
      this.fetchFriendRequestsSendFromUser(this.searchCriteria);
    } else if (tab === 'invites') {
      this.fetchFriendInvitesReceivedForUser(this.searchCriteria);
    }
  }
}
