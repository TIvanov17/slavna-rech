import { AsyncPipe, DecimalPipe } from '@angular/common';
import {
  Component,
  inject,
  Input,
  OnDestroy,
  OnInit,
  QueryList,
  ViewChildren,
} from '@angular/core';
import { Observable } from 'rxjs';

import { FormsModule } from '@angular/forms';
import { NgbHighlight, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import {
  NgbdSortableHeader,
  SortEvent,
} from '../../../components/table/sortable.directive';
import { UserService } from '../../../services/user.service';
import { PageFilter } from '../../../models/page.modes';
import { UserResponse } from '../../../models/user.models';
import { HttpParams } from '@angular/common/http';
import { ConnectionService } from '../../../services/connection.service';
import { GlobalStateService } from '../../../services/global-state.service';

@Component({
  standalone: true,
  selector: 'ngbd-table-complete',
  imports: [FormsModule, NgbHighlight, NgbdSortableHeader, NgbPaginationModule],
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css'],
})
export class UserTableComponent implements OnInit, OnDestroy {
  @Input() userSearchCriteria: PageFilter = {
    page: 1,
    pageSize: 2,
    searchKeyword: '',
    sortColumn: 'username',
    sortDirection: 'asc',
  };

  private userService = inject(UserService);
  private connectionService = inject(ConnectionService);

  private globalStateService = inject(GlobalStateService);
  users$: UserResponse[] = [];
  total$: number = 0;
  friends$: UserResponse[] = [];
  totalFriends$: number = 0;
  private searchTimeout: any;

  ngOnInit(): void {
    this.fetchUser();
    this.fetchFriends();
  }

  onPageChange() {
    this.fetchUser();
  }

  fetchUser(): void {
    const params = new HttpParams()
      .set('page', this.userSearchCriteria.page)
      .set('pageSize', this.userSearchCriteria.pageSize)
      .set('searchKeyword', this.userSearchCriteria.searchKeyword)
      .set('sortColumn', this.userSearchCriteria.sortColumn)
      .set('sortDirection', this.userSearchCriteria.sortDirection);

    this.userService.getAllUsers(params).subscribe((data) => {
      console.log(data.content);
      this.users$ = data.content;
      this.total$ = data.totalElements;
    });
  }

  fetchFriends(): void {
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    if (senderId) {
      const params = new HttpParams()
        .set('page', this.userSearchCriteria.page)
        .set('pageSize', this.userSearchCriteria.pageSize)
        .set('searchKeyword', this.userSearchCriteria.searchKeyword)
        .set('sortColumn', this.userSearchCriteria.sortColumn)
        .set('sortDirection', this.userSearchCriteria.sortDirection);

      this.userService.getFriendsOfUser(senderId, params).subscribe((data) => {
        console.log(data.content);
        this.friends$ = data.content;
        this.totalFriends$ = data.totalElements;
      });
    }
  }

  onSort(column: SortEvent): void {}

  onSearchChange() {
    if (this.searchTimeout) {
      clearTimeout(this.searchTimeout);
    }

    this.searchTimeout = setTimeout(() => {
      this.fetchUser();
    }, 500);
  }

  addFriend(userId: number): void {
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    console.log(senderId, 'senderId');
    if (senderId) {
      this.connectionService
        .sendFriendRequest({
          senderId: senderId,
          receiverId: userId,
        })
        .subscribe((data) => {});
    }
  }

  ngOnDestroy(): void {}
}
