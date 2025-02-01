package pu.fmi.slavnarech.services.message;

import java.util.List;
import pu.fmi.slavnarech.entities.message.dtos.MessageDTO;
import pu.fmi.slavnarech.entities.message.dtos.MessageRequest;

public interface MessageService {

  MessageDTO createMessage(MessageRequest messageRequest);

  List<MessageDTO> getHistoryOfMessagesByConnection(Long connectionId);
}
