package pu.fmi.slavnarech.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pu.fmi.slavnarech.entities.member.Member;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  private String username;

  private String email;

  private String password;

  private LocalDate createdOn;

  private boolean isActive;

  @OneToMany(mappedBy = "user")
  private List<Member> members;
}
