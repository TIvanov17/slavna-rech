package pu.fmi.slavnarech.entities.connection.dtos;

import lombok.Data;

@Data
public class UpdateChannelRequest {

  private Long connectionId;

  private String name;

  private String description;
}
