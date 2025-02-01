package pu.fmi.slavnarech.services.user;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pu.fmi.slavnarech.annotations.TransactionalService;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserConnectionResponse;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.exceptions.NotFoundException;
import pu.fmi.slavnarech.repositories.MemberRepository;
import pu.fmi.slavnarech.repositories.connection.ConnectionRepository;
import pu.fmi.slavnarech.repositories.user.UserRepository;
import pu.fmi.slavnarech.repositories.user.UserSpecification;
import pu.fmi.slavnarech.services.connection.ConnectionFactory;
import pu.fmi.slavnarech.utils.PageFilter;

@TransactionalService
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private ConnectionRepository connectionRepository;

  @Autowired private MemberRepository memberRepository;

  @Autowired private UserMapper userMapper;

  @Autowired private ConnectionFactory connectionFactory;

  @Override
  public Page<UserResponse> searchAllByUsername(PageFilter pageFilter) {

    if(pageFilter == null){
      pageFilter = new PageFilter();
    }

    Page<User> page =
        userRepository.findAll(UserSpecification.searchByUsernameOrEmail(pageFilter.getSearchKeyword()), pageFilter);

    return page.map(user -> userMapper.mapToResponseDTO(user));
  }

  @Override
  public UserResponse saveUser(UserRequest userRequest) {
    User user = userMapper.mapToEntity(userRequest.getUsername(), userRequest.getEmail(), "");
    return userMapper.mapToResponseDTO(userRepository.save(user));
  }

  @Override
  public User registerUser(String username, String email, String password) {
    User user = userMapper.mapToEntity(username, email, password);
    return userRepository.save(user);
  }

  @Override
  public User getById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public User loadUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public boolean changeStatusOfFriendRequest(Long id, Long connectionId, MemberStatus status) {

    User user = getById(id);
    Optional<Connection> optionalConnection = connectionRepository.findById(connectionId);
    if (optionalConnection.isEmpty()) {
      throw new NotFoundException(Connection.class, connectionId);
    }

    Connection connection = optionalConnection.get();

    Member member = memberRepository.findByUserIdAndConnectionId(user.getId(), connection.getId());

    member.setStatus(status);
    return true;
  }

  @Override
  public Page<UserConnectionResponse> getFriendsOfUser(Long id, PageFilter pageFilter) {
    if(pageFilter == null){
      pageFilter = new PageFilter();
    }

    return userRepository.getFriendsOfUser(id, pageFilter);
  }


  @Override
  public Page<ConnectionResponse> getChannelsOfUser(Long id, PageFilter pageFilter) {
    if(pageFilter == null){
      pageFilter = new PageFilter();
    }

    Page<Connection> connections = userRepository.getChannels(id, pageFilter);
    return connections.map(connection -> connectionFactory.mapToResponse(connection));
  }

  @Override
  public Page<UserResponse> getFriendRequestsSendFromUser(Long id, PageFilter pageFilter) {
    if(pageFilter == null){
      pageFilter = new PageFilter();
    }

    Page<User> page = userRepository.findAll(UserSpecification.hasFriendsRequest(id), pageFilter);
    return page.map(user -> userMapper.mapToResponseDTO(user));
  }

  @Override
  public Page<UserConnectionResponse> getFriendInvitesReceivedForUser(Long id, PageFilter pageFilter) {
    if(pageFilter == null){
      pageFilter = new PageFilter();
    }

    return userRepository.getFriendInvitesFor(id, pageFilter);
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username);
  }
}
