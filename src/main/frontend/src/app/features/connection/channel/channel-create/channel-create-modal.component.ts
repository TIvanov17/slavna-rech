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
  NgbDatepickerModule,
  NgbModal,
} from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import {
  ChannelConnectionRequest,
  ConnectionResponse,
} from '../../../../models/connection.modes';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'channel-create-modal',
  imports: [CommonModule, NgbDatepickerModule, FormsModule],
  templateUrl: './channel-create-modal.component.html',
})
export class ChannelCreateModal {
  @Input() channelConnectionRequest: ChannelConnectionRequest = {
    userId: -1,
    name: '',
    description: '',
  };

  @Output() saveAction = new EventEmitter<void>();
  closeResult = '';
  @ViewChild('content', { static: true }) content!: TemplateRef<any>;
  @Input() selectedChannel: ConnectionResponse | null = null;
  constructor(private modalService: NgbModal) {}

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
