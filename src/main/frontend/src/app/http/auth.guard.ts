import { CanActivate, Router } from '@angular/router';
import { JWTService } from '../services/jwt.service';
import { inject, Injectable } from '@angular/core';
import { GlobalStateService } from '../services/global-state.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  private globalStateService = inject(GlobalStateService);
  private router = inject(Router);

  canActivate(): boolean {
    if (this.globalStateService.userDetails?.token) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
