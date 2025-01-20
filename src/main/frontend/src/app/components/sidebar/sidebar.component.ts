import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Tabs } from '../tabs/tab.component';

@Component({
  standalone: true,
  selector: 'sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  imports: [NgbModule],
})
export class Sidebar {
  @Input() collection: any[] = [];
  @Output() tabSelected = new EventEmitter<string>();
  @Output() userSelected = new EventEmitter<number>();
  selectedUserId: number | null = null;

  selectUser(userId: number): void {
    this.selectedUserId = userId;
    this.userSelected.emit(userId);
  }
}
