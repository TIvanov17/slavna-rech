import { Component, inject, Input } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  imports: [RouterLink, NgbModule, CommonModule],
})
export class NavigationBar {
  private router = inject(Router);
  @Input() searchQuery: string = '';

  onSearch(query: string): void {
    console.log('Search query:', query);
  }

  performSearch(): void {
    console.log('Search button clicked with query:', this.searchQuery);
  }

  onSignInClick() {
    this.router.navigate(['login']);
  }
}
