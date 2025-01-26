import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationRequest } from '../../../models/authentication.models';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../../services/authentication.service';
import { GlobalStateService } from '../../../services/global-state.service';
import { AuthFormComponent } from '../common/auth-form.component';

@Component({
  standalone: true,
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [AuthFormComponent, FormsModule, CommonModule],
})
export class LoginPage {
  authRequest: AuthenticationRequest = { username: '', password: '' };
  errorMessages: string[] = [];

  private router = inject(Router);
  private authenticationService = inject(AuthenticationService);
  private globalStateService = inject(GlobalStateService);

  public login() {
    this.errorMessages = [];
    this.authenticationService.authenticate(this.authRequest).subscribe({
      next: (response) => {
        if (response == null) {
          console.log('User not found!');
          return;
        }
        this.globalStateService.userDetails = response;
        this.router.navigate(['channels']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  public onRegisterRedirect() {
    this.router.navigate(['register']);
  }
}
