import { CanActivate, Router } from '@angular/router';
import { JWTService } from '../services/jwt.service';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class GuestGuard implements CanActivate {
  private jwtService = inject(JWTService);
  private router = inject(Router);

  canActivate(): boolean {
    if (this.jwtService.isLoggedIn()) {
      this.router.navigate(['/channels']);
      return false;
    }
    return true;
  }
}
