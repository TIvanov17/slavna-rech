package pu.fmi.slavnarech.entities.member.dtos;

import lombok.Data;
import pu.fmi.slavnarech.entities.role.RoleName;

@Data
public class MemberRequest {

  private Long connectionId;

  private Long userId;

  private RoleName roleName;
}
