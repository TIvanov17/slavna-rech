package pu.fmi.slavnarech.services.connection;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pu.fmi.slavnarech.entities.connection.ChannelConnectionRequest;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.services.member.MemberFactory;

@Component
public class ConnectionFactory {

  @Autowired private MemberFactory memberFactory;

  public Connection mapToEntity(ChannelConnectionRequest channelConnectionRequest) {
    return Connection.builder()
        .name(channelConnectionRequest.getName())
        .description(channelConnectionRequest.getDescription())
        .connectionType(channelConnectionRequest.getConnectionType())
        .createdOn(LocalDate.now())
        .isActive(Boolean.TRUE)
        .build();
  }

  public ConnectionResponse mapToResponse(Connection connection) {
    return ConnectionResponse.builder()
        .id(connection.getId())
        .name(connection.getName())
        .description(connection.getDescription())
        .connectionType(connection.getConnectionType())
        .createdOn(connection.getCreatedOn())
        .connectionType(connection.getConnectionType())
        .members(
            connection.getMembers().stream()
                .map(member -> memberFactory.mapToResponse(member))
                .toList())
        .isActive(connection.isActive())
        .build();
  }
}
