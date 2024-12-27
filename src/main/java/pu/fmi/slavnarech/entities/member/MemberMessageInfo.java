package pu.fmi.slavnarech.entities.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Data;
import pu.fmi.slavnarech.entities.message.Message;
import pu.fmi.slavnarech.entities.message.MessageStatus;

@Data
@Entity
public class MemberMessageInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "messageId")
  private Message message;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "userId")
  @JoinColumn(name = "connection_id", referencedColumnName = "connectionId")
  private Member receiver;

  @Enumerated(EnumType.STRING)
  private MessageStatus messageStatus;

  private LocalDateTime seenOnDate;
}
