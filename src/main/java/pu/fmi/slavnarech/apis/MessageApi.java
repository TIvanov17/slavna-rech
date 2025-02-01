package pu.fmi.slavnarech.apis;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pu.fmi.slavnarech.annotations.ValidRestApi;
import pu.fmi.slavnarech.entities.message.dtos.MessageResponse;
import pu.fmi.slavnarech.services.message.MessageService;

@ValidRestApi("api/v1/messages")
public class MessageApi {

  @Autowired private MessageService messageService;

  @GetMapping
  public List<MessageResponse> getMessageForConnection(@RequestParam Long connectionId){
    return messageService.getHistoryOfMessagesByConnection(connectionId);
  }
}
