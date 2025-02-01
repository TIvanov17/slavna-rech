package pu.fmi.slavnarech.entities.message.dtos;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {

  private Long receiverId;
  private Long senderId;
  private Long connectionId;
  private String content;
  private LocalDateTime createdOn;
}
