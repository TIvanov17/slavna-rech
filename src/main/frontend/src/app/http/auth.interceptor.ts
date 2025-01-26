import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { JWTService } from '../services/jwt.service';
import { GlobalStateService } from '../services/global-state.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const globalStateService = inject(GlobalStateService);
  const token = globalStateService.userDetails?.token;
  console.log(req.url);

  if (token && !req.url.includes('/register') && !req.url.includes('/login')) {
    const clonedReq = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    });
    return next(clonedReq);
  }

  return next(req);
};
