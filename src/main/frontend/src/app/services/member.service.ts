import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { MemberRequest, MemberResponse } from '../models/member.models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MemberService {
  private httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/members';

  public getMemberByUserAndConnectionId(
    userId: number,
    connectionId: number
  ): Observable<MemberResponse> {
    return this.httpClient.get<MemberResponse>(
      `${this.baseUrl}/users/${userId}/connections/${connectionId}`
    );
  }

  public updateRole(memberRequest: MemberRequest): Observable<void> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.put<any>(`${this.baseUrl}/role`, memberRequest, {
      headers,
    });
  }
}
