import { Injectable } from '@angular/core';
import { UserDetails } from '../models/authentication.models';
import { RoleName } from '../enums/role-name.enums';

@Injectable({
  providedIn: 'root',
})
export class GlobalStateService {
  private _userDetails: UserDetails | null = null;
  private _role: RoleName | null = null;

  set userDetails(userDetails: UserDetails | null) {
    this._userDetails = userDetails;
  }

  get userDetails(): UserDetails | null {
    return this._userDetails;
  }

  set role(role: RoleName) {
    this._role = role;
  }

  get role(): RoleName | null {
    return this._role;
  }
}
