package pu.fmi.slavnarech.controllers.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

  @MessageMapping("/hello")
  @SendTo("/topic/greeting")
  public String greeting(String message) {
    System.out.println("Received message: " + message);
    return HtmlUtils.htmlEscape(message);
  }
}
