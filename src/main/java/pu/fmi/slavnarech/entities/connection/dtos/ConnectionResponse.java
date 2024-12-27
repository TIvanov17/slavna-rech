package pu.fmi.slavnarech.entities.connection.dtos;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import pu.fmi.slavnarech.entities.connection.ConnectionType;
import pu.fmi.slavnarech.entities.member.dtos.MemberResponse;

@Data
@Builder
public class ConnectionResponse {

  private Long id;

  private String name;

  private String description;

  private LocalDate createdOn;

  private ConnectionType connectionType;

  private boolean isActive;

  private List<MemberResponse> members;
}
