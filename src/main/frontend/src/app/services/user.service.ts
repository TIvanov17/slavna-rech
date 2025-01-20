import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { UserRequest, UserResponse } from '../models/user.models';
import { Observable } from 'rxjs';
import { Page } from '../models/page.modes';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/users';

  public createUser(userRequest: UserRequest) {
    return this.httpClient.post(this.baseUrl, userRequest);
  }

  public getAllUsers(): Observable<Page<UserResponse>> {
    return this.httpClient.get<Page<UserResponse>>(this.baseUrl);
  }
}
