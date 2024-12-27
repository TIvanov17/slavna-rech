package pu.fmi.slavnarech.services.user;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;

@Component
public class UserMapper {

  public User mapToEntity(UserRequest userRequest) {
    return User.builder()
        .username(userRequest.getUsername())
        .email(userRequest.getEmail())
        .password("")
        .createdOn(LocalDate.now())
        .build();
  }

  public UserResponse mapToResponseDTO(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .isActive(user.isActive())
        .createdOn(user.getCreatedOn())
        .build();
  }
}
