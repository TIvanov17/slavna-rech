package pu.fmi.slavnarech.entities;

import java.time.LocalDate;
import lombok.Data;

@Data
public class User {

  private Long id;

  private String username;

  private String email;

  private String password;

  private LocalDate createdOn;

  private boolean isActive;
}
