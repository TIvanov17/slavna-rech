package pu.fmi.slavnarech.services.security;

import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.entities.user.security.AuthenticationRequest;
import pu.fmi.slavnarech.entities.user.security.AuthenticationResponse;
import pu.fmi.slavnarech.entities.user.security.RegistrationRequest;

public interface AuthenticationService {

  AuthenticationResponse register(RegistrationRequest registrationRequest);

  AuthenticationResponse authenticate(AuthenticationRequest authRequest);

  AuthenticationResponse refreshToken(String refreshToken);

  UserResponse getCurrentLoggedInUser();
}
