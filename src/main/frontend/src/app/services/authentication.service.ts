import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import {
  AuthenticationRequest,
  UserDetails,
} from '../models/authentication.models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/auth';

  public authenticate(authRequest: AuthenticationRequest) {
    return this.httpClient.post<UserDetails>(
      `${this.baseUrl}/login`,
      authRequest,
      {
        withCredentials: true,
      }
    );
  }

  public register(authRequest: AuthenticationRequest) {
    return this.httpClient.post<UserDetails>(
      `${this.baseUrl}/register`,
      authRequest
    );
  }

  public getCurrentLoggedUser() {
    return this.httpClient.get<UserDetails>(`${this.baseUrl}/current-user`);
  }
}
