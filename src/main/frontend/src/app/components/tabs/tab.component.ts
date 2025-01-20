import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { Sidebar } from '../sidebar/sidebar.component';
import { UserResponse } from '../../models/user.models';
import { TextInput } from '../text-input/text-input.component';

@Component({
  standalone: true,
  selector: 'tabs',
  imports: [NgbNavModule, NgbModule, Sidebar, TextInput],
  templateUrl: './tab.component.html',
  styleUrl: './tab.component.css',
})
export class Tabs {
  active = 1;
  @Input() friends: UserResponse[] = [];
  @Input() channels: UserResponse[] = [];
}
