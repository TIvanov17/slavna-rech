package pu.fmi.slavnarech.apis;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pu.fmi.slavnarech.annotations.ValidRestApi;
import pu.fmi.slavnarech.entities.connection.dtos.ChannelConnectionRequest;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.connection.dtos.FriendConnectionRequest;
import pu.fmi.slavnarech.services.connection.ConnectionService;

@ValidRestApi("api/v1/connection")
public class ConnectionApi {

  @Autowired private ConnectionService connectionService;

  @PostMapping("/channel")
  public ResponseEntity<ConnectionResponse> createChannel(
      @Valid @RequestBody ChannelConnectionRequest channelConnectionRequest) {
    return ResponseEntity.ok(connectionService.createChannel(channelConnectionRequest));
  }

  @PostMapping("/friend")
  public ResponseEntity<ConnectionResponse> createFriendRequest(
      @Valid @RequestBody FriendConnectionRequest friendConnectionRequest) {

    return ResponseEntity.ok(
        connectionService.createFriendConnectionRequest(
            friendConnectionRequest.getSenderId(), friendConnectionRequest.getReceiverId()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ConnectionResponse> getConnectionById(@PathVariable Long id) {
    return ResponseEntity.ok(connectionService.getById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteConnection(@PathVariable Long id) {
    return ResponseEntity.ok(connectionService.deleteConnection(id));
  }
}
