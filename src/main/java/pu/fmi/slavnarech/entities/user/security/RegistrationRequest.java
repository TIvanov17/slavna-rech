package pu.fmi.slavnarech.entities.user.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationRequest {

  @NotBlank(message = "Username is mandatory")
  private String username;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password is mandatory")
  private String password;
}
