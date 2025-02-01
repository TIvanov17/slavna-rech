import {
  Component,
  ContentChild,
  ContentChildren,
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
  QueryList,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbHighlight, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import {
  NgbdSortableHeader,
  SortEvent,
} from '../../../components/table/sortable.directive';
import { PageFilter } from '../../../models/page.modes';
import {
  UserConnectionResponse,
  UserResponse,
} from '../../../models/user.models';
import { GlobalStateService } from '../../../services/global-state.service';
import { IconComponent } from '../../../components/icon/icon.component';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'user-table',
  imports: [
    FormsModule,
    NgbHighlight,
    NgbdSortableHeader,
    NgbPaginationModule,
    CommonModule,
  ],
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css'],
})
export class UserTable implements OnInit, OnDestroy {
  private globalStateService = inject(GlobalStateService);

  @Input() userSearchCriteria: PageFilter = {
    page: 1,
    pageSize: 2,
    searchKeyword: '',
    sortColumn: 'username',
    sortDirection: 'asc',
  };
  @Input() isScrollable: boolean = false;
  @Input() users$: UserConnectionResponse[] = [];
  @Input() total$: number = 0;
  @Input() totalPages$: number = 0;
  @Input() friends$: UserConnectionResponse[] = [];
  @Input() totalFriends$: number = 0;
  private searchTimeout: any;
  isLoading = false;

  currentUser = this.globalStateService.userDetails?.currentUser;

  @Output() fetchUsers = new EventEmitter<number>();
  @Output() actionEvent = new EventEmitter<number>();
  @Output() actionRemoveEvent = new EventEmitter<number>();
  @ContentChild(IconComponent) addFriendIcon?: IconComponent;
  @ContentChildren(IconComponent) icons: QueryList<IconComponent> =
    new QueryList<any>();

  ngAfterContentInit() {
    this.icons.toArray().forEach((icon) => {
      icon.iconClick.subscribe(() => {
        console.log('Icon clicked!');
      });
    });
  }

  ngOnInit(): void {
    this.fetchUsers.emit();
  }

  onPageChange(): void {
    this.fetchUsers.emit();
  }

  onSearchChange(): void {
    if (this.searchTimeout) {
      clearTimeout(this.searchTimeout);
    }

    this.searchTimeout = setTimeout(() => {
      this.fetchUsers.emit();
    }, 500);
  }

  onSort(column: SortEvent): void {}

  onScroll(event: any): void {
    const bottom =
      event.target.scrollHeight ===
      Math.round(event.target.scrollTop + event.target.clientHeight);
    if (bottom && !this.isLoading) {
      this.loadMoreUsers();
    }
  }

  loadUsers(): void {
    setTimeout(() => {
      this.isLoading = false;
    }, 1000);
  }

  loadMoreUsers(): void {
    this.isLoading = true;
    setTimeout(() => {
      this.userSearchCriteria.page += 1;
      if (this.totalPages$ >= this.userSearchCriteria.page) {
        this.fetchUsers.emit();
      }
      this.isLoading = false;
    }, 1000);
  }

  trackById(index: number, user: any): number {
    return user.id;
  }

  ngOnDestroy(): void {
    if (this.searchTimeout) {
      clearTimeout(this.searchTimeout);
    }
  }
}
