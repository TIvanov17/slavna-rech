import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { JWTService } from '../services/jwt.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const jwtService = inject(JWTService);
  const token = jwtService.getToken();

  if (token && !req.url.includes('/register') && !req.url.includes('/login')) {
    const clonedReq = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    });
    return next(clonedReq);
  }

  return next(req);
};
