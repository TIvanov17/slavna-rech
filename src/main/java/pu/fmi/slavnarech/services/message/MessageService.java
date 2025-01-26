package pu.fmi.slavnarech.services.message;

import pu.fmi.slavnarech.entities.message.dtos.MessageRequest;
import pu.fmi.slavnarech.entities.message.dtos.MessageResponse;

public interface MessageService {

  MessageResponse createMessage(MessageRequest messageRequest);

}
