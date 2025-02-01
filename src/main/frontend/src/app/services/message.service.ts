import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { MessageDTO } from '../models/message.model';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/messages';

  public getMessageForConnection(connectionId: number) {
    const params = new HttpParams().set('connectionId', connectionId);
    return this.httpClient.get<MessageDTO[]>(this.baseUrl, { params });
  }
}
