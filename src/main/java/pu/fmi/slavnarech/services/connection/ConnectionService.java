package pu.fmi.slavnarech.services.connection;

import pu.fmi.slavnarech.entities.connection.dtos.ChannelConnectionRequest;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.connection.dtos.UpdateChannelRequest;

public interface ConnectionService {

  ConnectionResponse createChannel(ChannelConnectionRequest connectionRequest);

  ConnectionResponse updateChannel(UpdateChannelRequest updateChannelRequest);

  ConnectionResponse createFriendConnectionRequest(Long senderId, Long receiverId);

  ConnectionResponse addUserToChannel(Long connectionId, Long userId);

  ConnectionResponse getById(Long id);

  boolean deleteConnection(Long id);

  ConnectionResponse removeUserFromChannel(Long connectionId, Long userId);
}
