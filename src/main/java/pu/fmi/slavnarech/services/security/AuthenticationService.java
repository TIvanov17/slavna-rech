package pu.fmi.slavnarech.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import pu.fmi.slavnarech.entities.user.security.AuthenticationRequest;
import pu.fmi.slavnarech.entities.user.security.AuthenticationResponse;

public interface AuthenticationService {

  AuthenticationResponse register(AuthenticationRequest authRequest);

  AuthenticationResponse authenticate(AuthenticationRequest authRequest);

  AuthenticationResponse getRefreshToken(String refreshToken);

  UserDetails getCurrentLoggedInUser();
}
