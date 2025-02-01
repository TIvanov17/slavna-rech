package pu.fmi.slavnarech.repositories.message;

import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.slavnarech.entities.message.Message;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {}
