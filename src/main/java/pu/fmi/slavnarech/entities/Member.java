package pu.fmi.slavnarech.entities;

import java.time.LocalDate;
import javax.management.relation.Role;
import lombok.Data;

@Data
public class Member {

  private User user;

  private Connection connection;

  private LocalDate joinDate;

  private Role role;

  private boolean isActive;

}
