package pu.fmi.slavnarech.services.user;

import org.springframework.data.domain.Page;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.utils.PageFilter;

public interface UserService {

  Page<UserResponse> searchAllByUsername(String username, PageFilter pageFilter);

  UserResponse saveUser(UserRequest userRequest);

  User getUserById(Long id);
}
