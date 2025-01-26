package pu.fmi.slavnarech.services.message;

import java.time.LocalDateTime;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.message.Message;
import pu.fmi.slavnarech.entities.message.dtos.MessageRequest;
import pu.fmi.slavnarech.entities.message.dtos.MessageResponse;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.repositories.MessageRepository;
import pu.fmi.slavnarech.services.connection.ConnectionService;
import pu.fmi.slavnarech.services.member.MemberService;
import pu.fmi.slavnarech.services.user.UserService;

public class MessageServiceImpl implements MessageService {

  private UserService userService;

  private ConnectionService connectionService;

  private MemberService memberService;

  private MessageRepository messageRepository;

  @Override
  public MessageResponse createMessage(MessageRequest messageRequest) {

    User userSender = userService.getById(messageRequest.getSenderId());
    User userReceiver = userService.getById(messageRequest.getReceiverId());

    // friend connection between 1 and 2
    Member member = memberService.getByUserAndConnection(userSender.getId(), messageRequest.getConnectionId());

    Message message = Message.builder()
        .sender(member)
        .content(messageRequest.getContent())
        .createdOn(LocalDateTime.now())
        .isActive(Boolean.TRUE)
        .build();

    messageRepository.save(message);

    return MessageResponse.builder()
        .receiverId(2L)
        .content(message.getContent())
        .createdOn(message.getCreatedOn())
        .build();
  }
}
