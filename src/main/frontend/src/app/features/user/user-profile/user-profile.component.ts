import { CommonModule } from '@angular/common';
import {
  Component,
  Input,
  OnInit,
  TemplateRef,
  ViewChild,
  inject,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MemberResponse } from '../../../models/member.models';
import { RoleName } from '../../../enums/role-name.enums';
import { MemberService } from '../../../services/member.service';
import { GlobalStateService } from '../../../services/global-state.service';
import { ConnectionService } from '../../../services/connection.service';

@Component({
  standalone: true,
  selector: 'user-profile',
  imports: [FormsModule, CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfile implements OnInit {
  RoleName = RoleName;
  closeResult = '';

  @ViewChild('content', { static: true }) content!: TemplateRef<any>;
  @Input() user: MemberResponse | null = null;

  private memberService = inject(MemberService);
  private connectionService = inject(ConnectionService);
  globalStateService = inject(GlobalStateService);
  userId = this.globalStateService.userDetails?.currentUser.id;
  role: RoleName | null | undefined = null;

  constructor(private modalService: NgbModal) {}

  onChangeRoleClicked(roleName: RoleName) {
    if (this.user?.userId && this.user?.connectionId) {
      this.memberService
        .updateRole({
          userId: this.user?.userId,
          connectionId: this.user?.connectionId,
          roleName: roleName,
        })
        .subscribe((data) => {
          data;
        });
    }
  }

  onRemoveClicked(
    userId: number | undefined,
    connectionId: number | undefined
  ) {
    if (userId && connectionId) {
      this.connectionService
        .removeUserFromChannel(userId, connectionId)
        .subscribe((data) => {});
    }
  }

  open() {
    this.role = this.globalStateService.role;

    this.modalService
      .open(this.content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          if (this.userId && this.user?.connectionId) {
            this.role = this.globalStateService.role;
          }

          if (result === 'Save click') {
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

  ngOnInit(): void {
    if (this.userId && this.user?.connectionId) {
      this.role = this.globalStateService.role;
    }
  }

  ngOnDestroy(): void {}
}
