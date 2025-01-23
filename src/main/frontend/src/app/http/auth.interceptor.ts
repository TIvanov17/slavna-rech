import { inject, Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
} from '@angular/common/http';
import { JWTService } from '../services/jwt.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private jwtService = inject(JWTService);

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const token = this.jwtService.getToken();

    if (token) {
      const clonedReq = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
      return next.handle(clonedReq);
    }

    return next.handle(req);
  }
}
