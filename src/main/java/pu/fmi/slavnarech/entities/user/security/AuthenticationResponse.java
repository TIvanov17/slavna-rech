package pu.fmi.slavnarech.entities.user.security;

import lombok.Builder;
import lombok.Data;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;

@Data
@Builder
public class AuthenticationResponse {

  private String token;

  private String refreshToken;

  private UserResponse currentUser;
}
