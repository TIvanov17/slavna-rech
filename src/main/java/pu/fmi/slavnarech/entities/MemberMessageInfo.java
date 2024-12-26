package pu.fmi.slavnarech.entities;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MemberMessageInfo {

  private Long id;

  private Message message;

  private Member receiver;

  private MessageStatus messageStatus;

  private LocalDateTime seenOnDate;
}
