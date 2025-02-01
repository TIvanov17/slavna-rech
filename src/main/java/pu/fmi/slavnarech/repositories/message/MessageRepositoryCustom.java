package pu.fmi.slavnarech.repositories.message;

import java.util.List;
import pu.fmi.slavnarech.entities.message.dtos.MessageDTO;

public interface MessageRepositoryCustom {

  List<MessageDTO> findByConnectionId(Long connectionId);
}
