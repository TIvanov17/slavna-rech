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
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  imports: [AuthFormComponent, FormsModule, CommonModule],
})
export class RegisterPage {
  authRequest: AuthenticationRequest = {
    username: '',
    password: '',
    email: '',
  };
  errorMessages: string[] = [];

  private router = inject(Router);
  private authenticationService = inject(AuthenticationService);
  private globalStateService = inject(GlobalStateService);

  public register() {
    this.errorMessages = [];
    this.authenticationService.register(this.authRequest).subscribe({
      next: (response) => {
        this.globalStateService.userDetails = response;
        this.router.navigate(['channels']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  public onLoginRedirect() {
    this.router.navigate(['login']);
  }
}
