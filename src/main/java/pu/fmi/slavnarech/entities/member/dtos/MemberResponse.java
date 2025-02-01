package pu.fmi.slavnarech.entities.member.dtos;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.role.RoleName;

@Data
@Builder
public class MemberResponse {

  private Long connectionId;

  private Long userId;

  private String username;

  private String email;

  private LocalDate joinDate;

  private RoleName role;

  private boolean isActive;

  private MemberStatus status;
}
