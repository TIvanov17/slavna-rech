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

  @Input() users$: UserConnectionResponse[] = [];
  @Input() total$: number = 0;
  @Input() friends$: UserConnectionResponse[] = [];
  @Input() totalFriends$: number = 0;
  private searchTimeout: any;

  currentUser = this.globalStateService.userDetails?.currentUser;

  @Input() fetchUsers!: (criteria: PageFilter) => void;
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
    if (this.fetchUsers) {
      this.fetchUsers(this.userSearchCriteria);
    }
  }

  onPageChange(): void {
    this.fetchUsers(this.userSearchCriteria);
  }

  onSearchChange(): void {
    if (this.searchTimeout) {
      clearTimeout(this.searchTimeout);
    }

    this.searchTimeout = setTimeout(() => {
      this.fetchUsers(this.userSearchCriteria);
    }, 500);
  }

  onSort(column: SortEvent): void {}

  // addAction(userId: number): void {
  //   this.actionEvent.emit(userId);
  // }

  // removeAction(userId: number): void {
  //   this.actionRemoveEvent.emit(userId);
  // }

  ngOnDestroy(): void {
    if (this.searchTimeout) {
      clearTimeout(this.searchTimeout);
    }
  }
}
