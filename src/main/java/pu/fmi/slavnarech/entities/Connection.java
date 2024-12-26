package pu.fmi.slavnarech.entities;

import lombok.Data;

@Data
public class Connection {

  private Long id;

  private String name;

  private String description;

  private String createdOn;

  private ConnectionType connectionType;

  private boolean isActive;
}
