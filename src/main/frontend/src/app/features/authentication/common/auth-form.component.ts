import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthenticationRequest } from '../../../models/authentication.models';

@Component({
  standalone: true,
  selector: 'auth-form',
  templateUrl: './auth-form.component.html',
  styleUrls: ['./auth-form.component.css'],
  imports: [FormsModule, CommonModule],
})
export class AuthFormComponent {
  @Input() authRequest: AuthenticationRequest = { username: '', password: '' };
  @Input() errorMessages: string[] = [];
  @Input() formHeader: string = '';
  @Input() formRedirectLinkLabel: string = '';
  @Input() formRedirectMessage: string = '';
  @Output() formSubmit = new EventEmitter<{
    username: string;
    password: string;
  }>();
  @Output() onRedirect = new EventEmitter<void>();

  public submitForm() {
    this.formSubmit.emit({
      username: this.authRequest.username,
      password: this.authRequest.password,
    });
  }

  onRedirectClicked() {
    this.onRedirect.emit();
  }
}
