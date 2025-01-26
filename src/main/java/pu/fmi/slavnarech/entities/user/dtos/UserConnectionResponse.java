package pu.fmi.slavnarech.entities.user.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserConnectionResponse {

  private Long connectionId;

  private LocalDate connectionCreatedOn;

  private Long id;

  private String username;

  private String email;

  private LocalDate createdOn;

  private boolean isActive;
}
