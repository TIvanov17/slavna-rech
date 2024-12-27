package pu.fmi.slavnarech.entities.connection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Connection {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "connection_id")
  private Long id;

  private String name;

  private String description;

  private LocalDate createdOn;

  @Enumerated(EnumType.STRING)
  private ConnectionType connectionType;

  private boolean isActive;

  @OneToMany(mappedBy = "connection")
  private List<Member> members;
}
