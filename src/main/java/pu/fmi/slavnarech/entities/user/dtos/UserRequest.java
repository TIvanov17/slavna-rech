package pu.fmi.slavnarech.entities.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

  @NotBlank(message = "User must have username")
  private String username;

  @NotBlank(message = "User must have email address")
  @Email(message = "Invalid email format")
  private String email;
}
