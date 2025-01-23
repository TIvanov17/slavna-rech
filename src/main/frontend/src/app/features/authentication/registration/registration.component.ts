import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  AuthenticationRequest,
  RegistrationRequest,
} from '../../../models/authentication.models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  standalone: true,
  selector: 'login',
  templateUrl: './registration.component.html',
  imports: [FormsModule, CommonModule],
})
export class RegisterPage {
  registrationRequest: RegistrationRequest = {
    username: '',
    email: '',
    password: '',
  };
  errorMessages: String[] = [];

  private router = inject(Router);
  private authenticationService = inject(AuthenticationService);

  public register() {
    this.errorMessages = [];
    this.authenticationService.register(this.registrationRequest).subscribe({
      next: (response) => {
        localStorage.setItem('auth_token', response.token);
        this.router.navigate(['channels']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  public login() {
    this.router.navigate(['login']);
  }
}
