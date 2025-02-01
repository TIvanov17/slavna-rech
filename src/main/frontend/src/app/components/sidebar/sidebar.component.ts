import {
  Component,
  EventEmitter,
  inject,
  Input,
  Output,
  signal,
  TemplateRef,
  ViewChild,
  WritableSignal,
} from '@angular/core';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModule,
} from '@ng-bootstrap/ng-bootstrap';
import { UserConnectionResponse, UserResponse } from '../../models/user.models';
import { ChannelCreateModal } from '../../features/connection/channel/channel-create/channel-create-modal.component';
import { ConnectionService } from '../../services/connection.service';
import { GlobalStateService } from '../../services/global-state.service';
import {
  ChannelConnectionRequest,
  ConnectionResponse,
} from '../../models/connection.modes';

@Component({
  standalone: true,
  selector: 'sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  imports: [NgbModule, ChannelCreateModal],
})
export class Sidebar {
  // private selectedUserId: UserConnectionResponse | null = null;
  // private selectedChannel: ConnectionResponse | null = null;
  // private isModalOpen = false;

  @Input() isChannel: boolean = false;
  @Input() friends: any[] = [];
  @Input() channels: any[] = [];
  @Output() tabSelected = new EventEmitter<string>();
  @Output() userSelected = new EventEmitter<UserConnectionResponse>();
  @Output() channelSelected = new EventEmitter<ConnectionResponse>();
  @Output() fetchChannels = new EventEmitter<void>();

  @ViewChild('channelModal') channelModal!: ChannelCreateModal;

  private connectionService = inject(ConnectionService);
  private globalStateService = inject(GlobalStateService);

  channelConnectionRequest: ChannelConnectionRequest = {
    userId: -1,
    name: '',
    description: '',
  };

  openChannelModal() {
    this.channelModal.open();
  }

  closeChannelModal() {
    this.channelModal.open();
  }

  createChannel() {
    const userId = this.globalStateService.userDetails?.currentUser.id;
    if (userId) {
      this.channelConnectionRequest.userId = userId;
      this.connectionService
        .createChannel(this.channelConnectionRequest)
        .subscribe((data) => {
          console.log(data);
          this.fetchChannels.emit();
        });
    }
  }

  selectUser(user: UserConnectionResponse): void {
    // this.selectedUserId = user;
    this.userSelected.emit(user);
  }

  selectChannel(channel: ConnectionResponse): void {
    // this.selectedChannel = channel;
    this.channelSelected.emit(channel);
  }
}
