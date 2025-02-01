package pu.fmi.slavnarech.services.message;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.message.Message;
import pu.fmi.slavnarech.entities.message.dtos.MessageRequest;
import pu.fmi.slavnarech.entities.message.dtos.MessageResponse;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.repositories.MessageRepository;
import pu.fmi.slavnarech.services.connection.ConnectionService;
import pu.fmi.slavnarech.services.member.MemberService;
import pu.fmi.slavnarech.services.user.UserService;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired
  private UserService userService;

  @Autowired
  private ConnectionService connectionService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private MessageRepository messageRepository;

  @Override
  public MessageResponse createMessage(MessageRequest messageRequest) {

    User userSender = userService.getById(messageRequest.getSenderId());
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

  @Override
  public List<MessageResponse> getHistoryOfMessagesByConnection(Long connectionId) {
    List<Message> messages = messageRepository.findBySenderConnectionId(connectionId);
    return messages.stream().map(message -> MessageResponse.builder().content(message.getContent()).build()).toList();
  }
}
