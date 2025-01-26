package pu.fmi.slavnarech.controllers.websocket;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import pu.fmi.slavnarech.entities.message.dtos.MessageRequest;

@Controller
public class GreetingController {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/hello")
  @SendTo("/topic/greeting")
  public String greeting(String message) {
    System.out.println("Received message: " + message);
    return HtmlUtils.htmlEscape(message);
  }

  @MessageMapping("/private-message")
  public void getPrivateMessage(@Payload MessageRequest messageRequest) {
    System.out.println("Received message: " + messageRequest.getContent());
    System.out.println("username: " + messageRequest.getReceiverId());
    messagingTemplate.convertAndSendToUser(String.valueOf(messageRequest.getReceiverId()),
        "/topic/private-message",
        HtmlUtils.htmlEscape("Private message from " + ": " + messageRequest.getReceiverId()));
  }

//  @MessageMapping("/private-message/{userId}")
//  public void getPrivateMessage(String message, @DestinationVariable String userId) {
//    System.out.println("Received message: " + message);
//    System.out.println("username: " + userId);
//    messagingTemplate.convertAndSendToUser(userId,
//        "/topic/private-message",
//        HtmlUtils.htmlEscape("Private message from " + ": " + message));
//  }
}
