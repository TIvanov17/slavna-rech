import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import {
  AuthenticationRequest,
  AuthenticationResponse,
} from '../models/authentication.models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/auth';

  public authenticate(
    authRequest: AuthenticationRequest
  ): Observable<AuthenticationResponse> {
    return this.httpClient.post(`${this.baseUrl}/login`, authRequest, {
      withCredentials: true,
    });
  }

  public register(
    authRequest: AuthenticationRequest
  ): Observable<AuthenticationResponse> {
    return this.httpClient.post(`${this.baseUrl}/register`, authRequest);
  }

  public getCurrentLoggedUser(): Observable<AuthenticationResponse> {
    return this.httpClient.get(`${this.baseUrl}/currentUser`);
  }
}
