package pu.fmi.slavnarech.entities.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Data;
import pu.fmi.slavnarech.entities.member.Member;

@Data
@Entity
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_id", referencedColumnName = "userId")
  @JoinColumn(name = "connection_id", referencedColumnName = "connectionId")
  private Member sender;

  private String content;

  private LocalDateTime createdOn;

  private boolean isActive;
}
