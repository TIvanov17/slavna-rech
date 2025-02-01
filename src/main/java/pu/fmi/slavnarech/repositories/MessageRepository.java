package pu.fmi.slavnarech.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.slavnarech.entities.message.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findBySenderConnectionId(Long connectionId);
}
