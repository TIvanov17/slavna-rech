package pu.fmi.slavnarech.apis;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pu.fmi.slavnarech.annotations.ValidRestApi;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.member.dtos.MemberResponse;
import pu.fmi.slavnarech.entities.user.dtos.UserConnectionResponse;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.services.member.MemberService;
import pu.fmi.slavnarech.services.user.UserService;
import pu.fmi.slavnarech.utils.PageFilter;

@ValidRestApi("api/v1/users")
public class UserApi {

  @Autowired private UserService userService;

  @Autowired private MemberService memberService;

  @GetMapping("/{id}/connections/{connectionId}")
  public ResponseEntity<MemberResponse> getUserById(@PathVariable Long id, @PathVariable Long connectionId){
    return ResponseEntity.ok(memberService.getMember(id, connectionId));
  }

  @GetMapping
  public ResponseEntity<Page<UserResponse>> listAllUsers(PageFilter pageFilter) {
    return ResponseEntity.ok(userService.searchAllByUsername(pageFilter));
  }

  @PostMapping
  public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
    return ResponseEntity.ok(userService.saveUser(userRequest));
  }

  @PutMapping("/{id}/connection/{connectionId}/member/{status}")
  public ResponseEntity<?> changeStatusOfMemberFriend(
      @PathVariable Long id, @PathVariable Long connectionId, @PathVariable MemberStatus status) {
    return ResponseEntity.ok(userService.changeStatusOfFriendRequest(id, connectionId, status));
  }

  @GetMapping("/{id}/connections/friends")
  public ResponseEntity<Page<UserConnectionResponse>> listFriendsOfUser(
      @PathVariable Long id, PageFilter pageFilter) {
    return ResponseEntity.ok(userService.getFriendsOfUser(id, pageFilter));
  }

  @GetMapping("/{id}/connections/channels")
  public ResponseEntity<Page<ConnectionResponse>> listChannelsOfUser(
      @PathVariable Long id, PageFilter pageFilter) {
    return ResponseEntity.ok(userService.getChannelsOfUser(id, pageFilter));
  }

  @GetMapping("/{id}/connections/friend-requests")
  public ResponseEntity<Page<UserResponse>> getFriendRequestsSendFromUser(
      @PathVariable Long id, PageFilter pageFilter) {
    return ResponseEntity.ok(userService.getFriendRequestsSendFromUser(id, pageFilter));
  }

  @GetMapping("/{id}/connections/friend-invites")
  public ResponseEntity<Page<UserConnectionResponse>> getFriendInvitesReceivedForUser(
      @PathVariable Long id, PageFilter pageFilter) {
    return ResponseEntity.ok(userService.getFriendInvitesReceivedForUser(id, pageFilter));
  }
}
