package pu.fmi.slavnarech.services.message;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.message.Message;
import pu.fmi.slavnarech.entities.message.dtos.MessageDTO;
import pu.fmi.slavnarech.entities.message.dtos.MessageRequest;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.repositories.message.MessageRepository;
import pu.fmi.slavnarech.services.connection.ConnectionService;
import pu.fmi.slavnarech.services.member.MemberService;
import pu.fmi.slavnarech.services.user.UserMapper;
import pu.fmi.slavnarech.services.user.UserService;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired private UserService userService;

  @Autowired private ConnectionService connectionService;

  @Autowired private MemberService memberService;

  @Autowired private MessageRepository messageRepository;

  @Autowired private UserMapper userMapper;

  @Override
  public MessageDTO createMessage(MessageRequest messageRequest) {

    User userSender = userService.getById(messageRequest.getSenderId());
    Member member =
        memberService.getByUserAndConnection(userSender.getId(), messageRequest.getConnectionId());

    Message message =
        Message.builder()
            .sender(member)
            .content(messageRequest.getContent())
            .createdOn(LocalDateTime.now())
            .isActive(Boolean.TRUE)
            .build();

    messageRepository.save(message);

    return MessageDTO.builder()
        .sender(userMapper.mapToResponseDTO(userSender))
        .content(message.getContent())
        .createdOn(message.getCreatedOn())
        .build();
  }

  @Override
  public List<MessageDTO> getHistoryOfMessagesByConnection(Long connectionId) {
    return messageRepository.findByConnectionId(connectionId);
  }
}
