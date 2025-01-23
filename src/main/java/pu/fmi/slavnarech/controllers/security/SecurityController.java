package pu.fmi.slavnarech.controllers.security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.entities.user.security.AuthenticationRequest;
import pu.fmi.slavnarech.entities.user.security.AuthenticationResponse;
import pu.fmi.slavnarech.entities.user.security.RegistrationRequest;
import pu.fmi.slavnarech.services.security.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class SecurityController {

  @Autowired private AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody @Valid RegistrationRequest registrationRequest) {
    return ResponseEntity.ok(authenticationService.register(registrationRequest));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(this.authenticationService.authenticate(request));
  }

  @GetMapping("/current-user")
  public ResponseEntity<UserResponse> currentUserName() {
    return ResponseEntity.ok(this.authenticationService.getCurrentLoggedInUser());
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody String refreshToken) {
    return ResponseEntity.ok(this.authenticationService.refreshToken(refreshToken));
  }
}
