package pu.fmi.slavnarech.entities.connection.dtos;

import lombok.Data;

@Data
public class FriendConnectionRequest {

  private Long senderId;

  private Long receiverId;
}
