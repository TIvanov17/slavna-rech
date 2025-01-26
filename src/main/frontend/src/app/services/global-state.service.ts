import { Injectable } from '@angular/core';
import { UserDetails } from '../models/authentication.models';

@Injectable({
  providedIn: 'root',
})
export class GlobalStateService {
  private _userDetails: UserDetails | null = null;

  set userDetails(userDetails: UserDetails | null) {
    this._userDetails = userDetails;
  }

  get userDetails(): UserDetails | null {
    return this._userDetails;
  }
}
