import { UserResponse } from './user.models';

export type AuthenticationRequest = {
  username: string;
  password: string;
  email?: string;
};

export type RegistrationRequest = {
  username: string;
  email: string;
  password: string;
};

export type UserDetails = {
  token: string;
  refreshToken: string;
  currentUser: UserResponse;
};
