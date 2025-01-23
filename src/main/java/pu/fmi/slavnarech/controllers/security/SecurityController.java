package pu.fmi.slavnarech.controllers.security;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pu.fmi.slavnarech.entities.user.security.AuthenticationRequest;
import pu.fmi.slavnarech.entities.user.security.AuthenticationResponse;
import pu.fmi.slavnarech.services.security.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class SecurityController {

  private AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<?> register(
      @RequestBody @Valid AuthenticationRequest authenticationRequest) {
    authenticationService.register(authenticationRequest);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @RequestBody AuthenticationRequest request, HttpServletResponse response) {

    AuthenticationResponse authenticationResponse =
        this.authenticationService.authenticate(request);

    return ResponseEntity.ok(authenticationResponse);
  }

  @GetMapping("/currentUser")
  public ResponseEntity<UserDetails> currentUserName() {
    return ResponseEntity.ok(this.authenticationService.getCurrentLoggedInUser());
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthenticationResponse> getRefreshToken(@RequestBody String refreshToken) {
    return ResponseEntity.ok(this.authenticationService.getRefreshToken(refreshToken));
  }
}
