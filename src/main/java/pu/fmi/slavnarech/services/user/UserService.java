package pu.fmi.slavnarech.services.user;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserConnectionResponse;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.utils.PageFilter;

public interface UserService extends UserDetailsService {

  Page<UserResponse> searchAllByUsername(PageFilter pageFilter);

  UserResponse saveUser(UserRequest userRequest);

  User registerUser(String username, String email, String password);

  User getById(Long id);

  User loadUserByEmail(String email);

  boolean changeStatusOfFriendRequest(Long id, Long connectionId, MemberStatus status);

  Page<UserConnectionResponse> getFriendsOfUser(Long id, PageFilter pageFilter);

  Page<ConnectionResponse> getChannelsOfUser(Long id, PageFilter pageFilter);

  Page<UserResponse> getFriendRequestsSendFromUser(Long id, PageFilter pageFilter);

  Page<UserConnectionResponse> getFriendInvitesReceivedForUser(Long id, PageFilter pageFilter);
}
