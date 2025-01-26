package pu.fmi.slavnarech.entities.message.dtos;

import lombok.Data;

@Data
public class MessageRequest {

  private Long senderId;

  private Long receiverId;

  private Long connectionId;

  private String content;

}
