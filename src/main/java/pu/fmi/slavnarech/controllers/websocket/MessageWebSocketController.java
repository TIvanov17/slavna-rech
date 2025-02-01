package pu.fmi.slavnarech.controllers.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pu.fmi.slavnarech.entities.message.dtos.MessageDTO;
import pu.fmi.slavnarech.entities.message.dtos.MessageRequest;
import pu.fmi.slavnarech.services.message.MessageService;

@Controller
public class MessageWebSocketController {

  @Autowired private SimpMessagingTemplate messagingTemplate;

  @Autowired private MessageService messageService;

  @MessageMapping("/private-message")
  public void getPrivateMessage(@Payload MessageRequest messageRequest) {
    System.out.println("Received message: " + messageRequest.getContent());
    System.out.println("username: " + messageRequest.getReceiverId());

    MessageDTO messageResponse = messageService.createMessage(messageRequest);

    messagingTemplate.convertAndSendToUser(
        String.valueOf(messageRequest.getConnectionId()),
        "/topic/private-message",
        messageResponse);
  }
}
