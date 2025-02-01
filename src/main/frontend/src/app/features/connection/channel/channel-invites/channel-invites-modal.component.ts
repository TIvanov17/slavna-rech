import {
  Component,
  EventEmitter,
  inject,
  Input,
  Output,
  TemplateRef,
  ViewChild,
} from '@angular/core';

import {
  ModalDismissReasons,
  NgbDatepickerModule,
  NgbModal,
} from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import {
  ChannelConnectionRequest,
  ConnectionResponse,
} from '../../../../models/connection.modes';
import { FormsModule } from '@angular/forms';
import { UserTable } from '../../../user/user-table/user-table.component';
import { PageFilter } from '../../../../models/page.modes';
import { UserConnectionResponse } from '../../../../models/user.models';
import { UserService } from '../../../../services/user.service';
import { GlobalStateService } from '../../../../services/global-state.service';
import { ConnectionService } from '../../../../services/connection.service';
import { IconComponent } from '../../../../components/icon/icon.component';
import { HttpParams } from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'channel-invites-modal',
  imports: [
    CommonModule,
    NgbDatepickerModule,
    FormsModule,
    UserTable,
    IconComponent,
  ],
  templateUrl: './channel-invites-modal.component.html',
})
export class ChannelInvitesModal {
  closeResult = '';
  users$: UserConnectionResponse[] = [];
  total$: number = 0;
  totalPages$: number = 0;
  searchCriteria: PageFilter = {
    page: 1,
    pageSize: 2,
    searchKeyword: '',
    sortColumn: 'username',
    sortDirection: 'asc',
  };

  @Input() channelConnectionRequest: ChannelConnectionRequest = {
    userId: -1,
    name: '',
    description: '',
  };
  @Input() selectedChannel: ConnectionResponse | null = null;
  @Output() saveAction = new EventEmitter<void>();
  @ViewChild('content', { static: true }) content!: TemplateRef<any>;

  constructor(private modalService: NgbModal) {}

  private userService = inject(UserService);
  private globalStateService = inject(GlobalStateService);
  private connectionService = inject(ConnectionService);

  fetchAllUsers = (criteria: PageFilter): void => {
    const params = this.getHttpParams(criteria);
    this.userService.getAllUsers(params).subscribe((data) => {
      this.users$ = [...this.users$, ...data.content];
      this.total$ = data.totalElements;
      this.totalPages$ = data.totalPages;
    });
  };

  addToChannel = (userId: number): void => {
    const senderId = this.globalStateService.userDetails?.currentUser.id;
    if (senderId && this.selectedChannel?.id) {
      this.connectionService
        .addUserToChannel(this.selectedChannel.id, userId)
        .subscribe((data) => {
          console.log('User added successfully');
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

  open() {
    this.modalService
      .open(this.content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          if (result === 'Save click') {
            this.saveAction.emit();
          }
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }

  private getDismissReason(reason: any): string {
    switch (reason) {
      case ModalDismissReasons.ESC:
        return 'by pressing ESC';
      case ModalDismissReasons.BACKDROP_CLICK:
        return 'by clicking on a backdrop';
      default:
        return `with: ${reason}`;
    }
  }
}
