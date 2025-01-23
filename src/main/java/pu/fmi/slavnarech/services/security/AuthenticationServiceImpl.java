package pu.fmi.slavnarech.services.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.security.AuthenticationRequest;
import pu.fmi.slavnarech.entities.user.security.AuthenticationResponse;
import pu.fmi.slavnarech.services.user.UserMapper;
import pu.fmi.slavnarech.services.user.UserService;

public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private JwtHelper jwtHelper;
  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private UserService userService;
  @Autowired private UserMapper userMapper;

  @Override
  public AuthenticationResponse register(AuthenticationRequest authRequest) {
    authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
    UserRequest userRequest = new UserRequest();
    return null;
    //    UserDetails user = userService.saveUser(null);
    //    String jwtToken = jwtService.generateToken(user);
    //    String refreshToken = jwtService.generateRefreshToken(user);
    //    return AuthenticationResponse.builder()
    //        .token(jwtToken)
    //        .refreshToken(refreshToken)
    //        .currentUser(null)
    //        .build();
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails user = userService.loadUserByEmail(authRequest.getEmail());
    String jwtToken = jwtHelper.generateToken(user);
    String refreshToken = jwtHelper.generateRefreshToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken)
        .currentUser(null)
        .build();
  }

  @Override
  public AuthenticationResponse getRefreshToken(String refreshToken) {
    DecodedJWT decodedToken = jwtHelper.decodedToken(refreshToken);
    String username = decodedToken.getSubject();
    UserDetails user = userService.loadUserByEmail(username);
    String accessToken = jwtHelper.generateToken(user);
    return AuthenticationResponse.builder()
        .token(accessToken)
        .refreshToken(refreshToken)
        .currentUser(null)
        .build();
  }

  @Override
  public UserDetails getCurrentLoggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      return userService.loadUserByEmail(authentication.getName());
    }
    return null;
  }
}
