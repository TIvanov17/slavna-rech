import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationRequest } from '../../../models/authentication.models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  standalone: true,
  selector: 'login',
  templateUrl: './login.component.html',
  imports: [FormsModule, CommonModule],
})
export class LoginPage {
  authRequest: AuthenticationRequest = { username: '', password: '' };
  errorMessages: String[] = [];

  private router = inject(Router);
  private authenticationService = inject(AuthenticationService);

  public login() {
    this.errorMessages = [];
    this.authenticationService.authenticate(this.authRequest).subscribe({
      next: (response) => {
        if (response == null) {
          console.log('User not found!');
          return;
        }
        localStorage.setItem('auth_token', response.token);
        this.router.navigate(['channels']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  public register() {
    this.router.navigate(['register']);
  }
}
