package pu.fmi.slavnarech.apis;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pu.fmi.slavnarech.annotations.ValidRestApi;
import pu.fmi.slavnarech.entities.connection.dtos.ChannelConnectionRequest;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.connection.dtos.FriendConnectionRequest;
import pu.fmi.slavnarech.entities.connection.dtos.UpdateChannelRequest;
import pu.fmi.slavnarech.services.connection.ConnectionService;

@ValidRestApi("api/v1/connections")
public class ConnectionApi {

  @Autowired
  private ConnectionService connectionService;

  @PostMapping("/channel")
  public ResponseEntity<ConnectionResponse> createChannel(
      @Valid @RequestBody ChannelConnectionRequest channelConnectionRequest) {
    return ResponseEntity.ok(connectionService.createChannel(channelConnectionRequest));
  }

  @PutMapping("/channel")
  public ResponseEntity<ConnectionResponse> updateChannel(
      @RequestBody UpdateChannelRequest updateChannelRequest) {
    return ResponseEntity.ok(connectionService.updateChannel(updateChannelRequest));
  }

  @PostMapping("/friend")
  public ResponseEntity<ConnectionResponse> createFriendRequest(
      @Valid @RequestBody FriendConnectionRequest friendConnectionRequest) {

    return ResponseEntity.ok(
        connectionService.createFriendConnectionRequest(
            friendConnectionRequest.getSenderId(), friendConnectionRequest.getReceiverId()));
  }

  @PutMapping("/channels/{connectionId}/user/{userId}")
  public ResponseEntity<ConnectionResponse> addUserToChannel(
      @PathVariable Long connectionId, @PathVariable Long userId) {

    return ResponseEntity.ok(connectionService.addUserToChannel(connectionId, userId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ConnectionResponse> getConnectionById(@PathVariable Long id) {
    return ResponseEntity.ok(connectionService.getById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteConnection(@PathVariable Long id) {
    return ResponseEntity.ok(connectionService.deleteConnection(id));
  }

  @DeleteMapping("/channels/{connectionId}/user/{userId}")
  public ResponseEntity<ConnectionResponse> removeUserFromChannel(
      @PathVariable Long connectionId, @PathVariable Long userId) {
    return ResponseEntity.ok(connectionService.removeUserFromChannel(connectionId, userId));
  }

}