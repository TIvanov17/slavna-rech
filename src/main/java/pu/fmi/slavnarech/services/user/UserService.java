package pu.fmi.slavnarech.services.user;

import java.util.List;
import org.springframework.data.domain.Page;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.utils.PageFilter;

public interface UserService {

  Page<UserResponse> searchAllByUsername(String username, PageFilter pageFilter);

  UserResponse saveUser(UserRequest userRequest);

  User getById(Long id);

  boolean changeStatusOfFriendRequest(Long id, Long connectionId, MemberStatus status);

  List<UserResponse> getFriendsOfUser(Long id);
}
