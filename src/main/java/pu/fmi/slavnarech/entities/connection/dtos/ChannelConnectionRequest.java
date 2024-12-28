package pu.fmi.slavnarech.entities.connection.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pu.fmi.slavnarech.entities.connection.ConnectionType;

@Data
public class ChannelConnectionRequest {

  @NotNull(message = "The user-creator is mandatory")
  private Long userId;

  @NotBlank(message = "The channel is mandatory and must be not blank")
  private String name;

  private String description;

  private final ConnectionType connectionType = ConnectionType.CHANNEL;
}
