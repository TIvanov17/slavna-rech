package pu.fmi.slavnarech.apis;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pu.fmi.slavnarech.annotations.ValidRestApi;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.services.user.UserService;
import pu.fmi.slavnarech.utils.PageFilter;

@ValidRestApi("api/v1/users")
@CrossOrigin("http://localhost:4200/")
public class UserApi {

  @Autowired private UserService userService;

  @GetMapping
  public ResponseEntity<Page<UserResponse>> listAllUsers(
      @RequestParam(required = false) String username, PageFilter pageFilter) {
    return ResponseEntity.ok(userService.searchAllByUsername(username, pageFilter));
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
  public ResponseEntity<?> listFriendsOfUser(@PathVariable Long id, PageFilter pageFilter) {
    return ResponseEntity.ok(userService.getFriendsOfUser(id));
  }
}
