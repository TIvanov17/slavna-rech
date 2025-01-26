import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-icon',
  template: '<i [class]="iconClass" [title]="title"></i>',
})
export class IconComponent {
  @Input() iconClass: string = '';
  @Input() title: string = '';

  @Output() iconClick = new EventEmitter<number>();
}
