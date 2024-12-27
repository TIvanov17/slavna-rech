package pu.fmi.slavnarech.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.repositories.user.UserRepository;
import pu.fmi.slavnarech.repositories.user.UserSpecification;
import pu.fmi.slavnarech.utils.PageFilter;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private UserMapper userMapper;

  @Override
  public Page<UserResponse> searchAllByUsername(String username, PageFilter pageFilter) {

    Page<User> page =
        userRepository.findAll(
            UserSpecification.buildPredicateForSearchByUsernameOrEmail(username), pageFilter);

    return page.map(user -> userMapper.mapToResponseDTO(user));
  }

  @Override
  public UserResponse saveUser(UserRequest userRequest) {
    User user = userMapper.mapToEntity(userRequest);
    return userMapper.mapToResponseDTO(userRepository.save(user));
  }

  @Override
  public User getUserById(Long id) {
    return this.userRepository.findById(id).orElse(null);
  }
}
