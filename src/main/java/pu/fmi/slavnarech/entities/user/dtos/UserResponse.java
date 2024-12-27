package pu.fmi.slavnarech.entities.user.dtos;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

  private Long id;

  private String username;

  private String email;

  private String password;

  private LocalDate createdOn;

  private boolean isActive;
}
