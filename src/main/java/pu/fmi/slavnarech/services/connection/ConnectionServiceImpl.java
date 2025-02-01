package pu.fmi.slavnarech.services.connection;

import static pu.fmi.slavnarech.utils.OperationUtils.updateIfChanged;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import pu.fmi.slavnarech.annotations.TransactionalService;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.ConnectionType;
import pu.fmi.slavnarech.entities.connection.dtos.ChannelConnectionRequest;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.connection.dtos.UpdateChannelRequest;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.role.RoleName;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.exceptions.NotFoundException;
import pu.fmi.slavnarech.repositories.MemberRepository;
import pu.fmi.slavnarech.repositories.connection.ConnectionRepository;
import pu.fmi.slavnarech.services.member.MemberFactory;
import pu.fmi.slavnarech.services.role.RoleService;
import pu.fmi.slavnarech.services.user.UserService;

@TransactionalService
public class ConnectionServiceImpl implements ConnectionService {

  @Autowired private UserService userService;

  @Autowired private RoleService roleService;

  @Autowired private ConnectionRepository connectionRepository;

  @Autowired private MemberRepository memberRepository;

  @Autowired private ConnectionFactory connectionFactory;

  @Autowired private MemberFactory memberFactory;

  @Override
  public ConnectionResponse createChannel(ChannelConnectionRequest connectionRequest) {

    User user = userService.getById(connectionRequest.getUserId());
    if (user == null) {
      throw new NotFoundException(User.class, connectionRequest.getUserId());
    }

    Role ownerRole = roleService.getByName(RoleName.OWNER);

    Connection connection =
        connectionFactory.mapToEntity(
            connectionRequest.getName(),
            connectionRequest.getDescription(),
            connectionRequest.getConnectionType());
    connection = connectionRepository.save(connection);

    Member member = memberFactory.mapToEntity(user, connection, ownerRole, MemberStatus.ACCEPTED);
    connection.setMembers(List.of(memberRepository.save(member)));

    return connectionFactory.mapToResponse(connection);
  }

  @Override
  public ConnectionResponse updateChannel(UpdateChannelRequest updateChannelRequest) {
    Optional<Connection> optionalConnection =
        connectionRepository.findById(updateChannelRequest.getConnectionId());

    AtomicReference<ConnectionResponse> connectionResponse = new AtomicReference<>();
    optionalConnection.ifPresentOrElse(
        connection -> {
          updateIfChanged(
              connection.getName(), updateChannelRequest.getName(), connection::setName);
          updateIfChanged(
              connection.getDescription(),
              updateChannelRequest.getDescription(),
              connection::setDescription);
          connectionRepository.save(connection);
          connectionResponse.set(connectionFactory.mapToResponse(connection));
        },
        () -> {});

    return connectionResponse.get();
  }

  @Override
  public ConnectionResponse createFriendConnectionRequest(Long senderId, Long receiverId) {

    User userSender = userService.getById(senderId);
    User userReceiver = userService.getById(receiverId);

    Connection connection =
        connectionFactory.mapToEntity(
            userSender.getUsername() + " - " + userReceiver.getUsername(),
            null,
            ConnectionType.FRIENDS);
    connection = connectionRepository.save(connection);

    Role friendRole = roleService.getByName(RoleName.FRIEND);
    Member senderMember =
        memberFactory.mapToEntity(userSender, connection, friendRole, MemberStatus.ACCEPTED);
    Member receiverMember =
        memberFactory.mapToEntity(userReceiver, connection, friendRole, MemberStatus.INVITED);

    connection.setMembers(
        List.of(memberRepository.save(senderMember), memberRepository.save(receiverMember)));

    return connectionFactory.mapToResponse(connection);
  }

  @Override
  public ConnectionResponse addUserToChannel(Long connectionId, Long userId) {

    User user = userService.getById(userId);
    if (user == null) {
      return null;
    }

    Optional<Connection> optionalConnection = connectionRepository.findById(connectionId);
    if (optionalConnection.isEmpty()) {
      return null;
    }

    Optional<Member> existMember =
        optionalConnection.get().getMembers().stream()
            .filter(member -> member.getUser().getId().equals(user.getId()))
            .findFirst();

    if (existMember.isPresent()) {
      return null;
    }

    Role roleGuess = roleService.getByName(RoleName.GUESS);
    Member member =
        memberFactory.mapToEntity(user, optionalConnection.get(), roleGuess, MemberStatus.ACCEPTED);
    optionalConnection.get().getMembers().add(memberRepository.save(member));

    return connectionFactory.mapToResponse(optionalConnection.get());
  }

  @Override
  public ConnectionResponse getById(Long id) {
    return connectionFactory.mapToResponse(getConnection(id));
  }

  @Override
  public boolean deleteConnection(Long id) {
    Connection connection = getConnection(id);
    connection.setActive(Boolean.FALSE);
    return true;
  }

  private Connection getConnection(Long id) {
    Optional<Connection> optionalConnection = connectionRepository.findById(id);
    return optionalConnection.orElseThrow(() -> new NotFoundException(Connection.class, id));
  }

  @Override
  public ConnectionResponse removeUserFromChannel(Long connectionId, Long userId) {

    User user = userService.getById(userId);
    if (user == null) {
      return null;
    }

    Optional<Connection> optionalConnection = connectionRepository.findById(connectionId);
    if (optionalConnection.isEmpty()) {
      return null;
    }

    Optional<Member> existMember =
        optionalConnection.get().getMembers().stream()
            .filter(member -> member.getUser().getId().equals(user.getId()))
            .findFirst();

    if (existMember.isEmpty()) {
      return null;
    }

    existMember.get().setActive(false);
    memberRepository.save(existMember.get());

    return connectionFactory.mapToResponse(optionalConnection.get());
  }
}
