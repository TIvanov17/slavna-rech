package pu.fmi.slavnarech.entities.member;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import java.time.LocalDate;
import lombok.Data;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.user.User;

@Data
@Entity
public class Member {

  @EmbeddedId private MemberId id;

  @MapsId("userId")
  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "user_id")
  private User user;

  @MapsId("connectionId")
  @ManyToOne
  @JoinColumn(name = "connectionId", referencedColumnName = "connection_id")
  private Connection connection;

  private LocalDate joinDate;

  @ManyToOne
  @JoinColumn(name = "roleId")
  private Role role;

  private boolean isActive;
}
