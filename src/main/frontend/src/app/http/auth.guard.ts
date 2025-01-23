import { CanActivate, Router } from '@angular/router';
import { JWTService } from '../services/jwt.service';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  private jwtService = inject(JWTService);
  private router = inject(Router);

  canActivate(): boolean {
    if (this.jwtService.isLoggedIn()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
