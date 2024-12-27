package pu.fmi.slavnarech.services.connection;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import pu.fmi.slavnarech.annotations.TransactionalService;
import pu.fmi.slavnarech.entities.connection.ChannelConnectionRequest;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.role.RoleName;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.exceptions.NotFoundException;
import pu.fmi.slavnarech.repositories.ConnectionRepository;
import pu.fmi.slavnarech.repositories.MemberRepository;
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

    User user = userService.getUserById(connectionRequest.getUserId());
    if (user == null) {
      throw new NotFoundException(User.class, connectionRequest.getUserId());
    }

    Role role = roleService.getByName(RoleName.OWNER);

    Connection connection = connectionFactory.mapToEntity(connectionRequest);
    connection = connectionRepository.save(connection);

    Member member = memberFactory.mapToEntity(user, connection, role);
    connection.setMembers(List.of(memberRepository.save(member)));

    return connectionFactory.mapToResponse(connection);
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
}
