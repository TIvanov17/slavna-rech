package pu.fmi.slavnarech.services.connection;

import pu.fmi.slavnarech.entities.connection.ChannelConnectionRequest;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;

public interface ConnectionService {

  ConnectionResponse createChannel(ChannelConnectionRequest connectionRequest);

  ConnectionResponse getById(Long id);

  boolean deleteConnection(Long id);
}
