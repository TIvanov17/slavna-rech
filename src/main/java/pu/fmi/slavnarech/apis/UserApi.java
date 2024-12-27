package pu.fmi.slavnarech.apis;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pu.fmi.slavnarech.annotations.ValidRestApi;
import pu.fmi.slavnarech.entities.user.dtos.UserRequest;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;
import pu.fmi.slavnarech.services.user.UserService;
import pu.fmi.slavnarech.utils.PageFilter;

@ValidRestApi("api/v1/users")
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
}
