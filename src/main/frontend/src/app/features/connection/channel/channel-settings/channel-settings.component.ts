import { Component, inject, Input, ViewChild } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ChannelInvitesModal } from '../channel-invites/channel-invites-modal.component';
import {
  ChannelConnectionRequest,
  ConnectionResponse,
} from '../../../../models/connection.modes';
import { ChannelCreateModal } from '../channel-create/channel-create-modal.component';
import { ConnectionService } from '../../../../services/connection.service';

@Component({
  standalone: true,
  selector: 'channel-settings',
  templateUrl: './channel-settings.component.html',
  styleUrls: ['./channel-settings.component.css'],
  imports: [NgbModule, ChannelInvitesModal, ChannelCreateModal],
})
export class ChannelSettings {
  @ViewChild('channelModal') channelModal!: ChannelInvitesModal;
  @ViewChild('updateChannelModal') updateChannelModal!: ChannelCreateModal;
  @Input() selectedChannel: ConnectionResponse | null = null;

  private connectionService = inject(ConnectionService);

  channelConnectionRequest: ChannelConnectionRequest = {
    userId: -1,
    name: this.selectedChannel?.name || '',
    description: this.selectedChannel?.description || '',
  };

  openChannelModal() {
    this.channelModal.open();
  }

  openUpdateChannel() {
    this.updateChannelModal.open();
  }

  updateChannel() {
    if (this.selectedChannel?.id) {
      this.connectionService
        .updateChannel({
          connectionId: this.selectedChannel?.id,
          name: this.channelConnectionRequest.name,
          description: this.channelConnectionRequest.description,
        })
        .subscribe((data) => {});
    }
  }

  closeChannelModal() {
    this.channelModal.open();
  }
}
