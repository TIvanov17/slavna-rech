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
import org.springframework.stereotype.Service;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.entities.user.security.AuthenticationRequest;
import pu.fmi.slavnarech.entities.user.security.AuthenticationResponse;
import pu.fmi.slavnarech.entities.user.security.RegistrationRequest;
import pu.fmi.slavnarech.services.user.UserMapper;
import pu.fmi.slavnarech.services.user.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private JwtHelper jwtHelper;
  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private UserService userService;
  @Autowired private UserMapper userMapper;

  @Override
  public AuthenticationResponse register(RegistrationRequest registrationRequest) {
    UserDetails existUser = userService.loadUserByUsername(registrationRequest.getUsername());
    if (existUser != null) {
      return null;
    }
    registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
    User user =
        userService.registerUser(
            registrationRequest.getUsername(),
            registrationRequest.getEmail(),
            registrationRequest.getPassword());
    String jwtToken = jwtHelper.generateToken(user);
    String refreshToken = jwtHelper.generateRefreshToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken)
        .currentUser(userMapper.mapToResponseDTO(user))
        .build();
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
    UserDetails user = userService.loadUserByUsername(authRequest.getUsername());
    if (user == null) {
      return null;
    }
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwtToken = jwtHelper.generateToken(user);
    String refreshToken = jwtHelper.generateRefreshToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken)
        .currentUser(userMapper.mapToResponseDTO((User) user))
        .build();
  }

  @Override
  public AuthenticationResponse refreshToken(String refreshToken) {
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
  public UserResponse getCurrentLoggedInUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      return userMapper.mapToResponseDTO(
          (User) userService.loadUserByUsername(authentication.getName()));
    }
    return null;
  }
}
