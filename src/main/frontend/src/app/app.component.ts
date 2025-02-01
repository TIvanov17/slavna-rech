import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from './services/authentication.service';
import { NavigationBar } from './layout/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgbModule, NavigationBar],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  private authenticationService = inject(AuthenticationService);

  ngOnInit() {
    this.authenticationService.getCurrentLoggedUser().subscribe({
      next: (userDetails) => console.log('User details loaded:', userDetails),
      error: () => console.log('Failed to fetch user details.'),
    });
  }
}
