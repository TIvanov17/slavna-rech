package pu.fmi.slavnarech.services.user;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;

@Component
public class UserMapper {

  public User mapToEntity(String username, String email, String password) {
    return User.builder()
        .username(username)
        .email(email)
        .password(password)
        .createdOn(LocalDate.now())
        .isActive(Boolean.TRUE)
        .build();
  }

  public UserResponse mapToResponseDTO(User user) {
    if(user == null){
      return null;
    }
    return UserResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .createdOn(user.getCreatedOn())
        .isActive(user.isActive())
        .build();
  }

}
