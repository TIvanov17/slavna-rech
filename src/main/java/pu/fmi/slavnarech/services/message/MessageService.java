package pu.fmi.slavnarech.services.message;

import java.util.List;
import pu.fmi.slavnarech.entities.message.dtos.MessageRequest;
import pu.fmi.slavnarech.entities.message.dtos.MessageResponse;

public interface MessageService {

  MessageResponse createMessage(MessageRequest messageRequest);

  List<MessageResponse> getHistoryOfMessagesByConnection(Long connectionId);
}
