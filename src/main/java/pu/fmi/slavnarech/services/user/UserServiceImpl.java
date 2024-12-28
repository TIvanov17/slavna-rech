package pu.fmi.slavnarech.services.user;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import pu.fmi.slavnarech.annotations.TransactionalService;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.exceptions.NotFoundException;
import pu.fmi.slavnarech.repositories.MemberRepository;
import pu.fmi.slavnarech.repositories.connection.ConnectionRepository;
import pu.fmi.slavnarech.repositories.user.UserRepository;
import pu.fmi.slavnarech.repositories.user.UserSpecification;
import pu.fmi.slavnarech.utils.PageFilter;

@TransactionalService
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private ConnectionRepository connectionRepository;

  @Autowired private MemberRepository memberRepository;

  @Autowired private UserMapper userMapper;

  @Override
  public Page<UserResponse> searchAllByUsername(String username, PageFilter pageFilter) {

    Page<User> page =
        userRepository.findAll(UserSpecification.searchByUsernameOrEmail(username), pageFilter);

    return page.map(user -> userMapper.mapToResponseDTO(user));
  }

  @Override
  public UserResponse saveUser(UserRequest userRequest) {
    User user = userMapper.mapToEntity(userRequest);
    return userMapper.mapToResponseDTO(userRepository.save(user));
  }

  @Override
  public User getById(Long id) {
    return userRepository.findById(id).orElse(null);
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
  public List<UserResponse> getFriendsOfUser(Long id) {
    return userRepository.findAll(UserSpecification.hasFriends(id)).stream()
        .map(user -> userMapper.mapToResponseDTO(user))
        .toList();
  }
}
