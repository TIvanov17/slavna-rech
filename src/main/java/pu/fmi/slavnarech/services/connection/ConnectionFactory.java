package pu.fmi.slavnarech.services.connection;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.ConnectionType;
import pu.fmi.slavnarech.entities.connection.dtos.ConnectionResponse;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.services.member.MemberFactory;

@Component
public class ConnectionFactory {

  @Autowired private MemberFactory memberFactory;

  public Connection mapToEntity(String name, String description, ConnectionType connectionType) {
    return Connection.builder()
        .name(name)
        .description(description)
        .connectionType(connectionType)
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
                .filter(Member::isActive)
                .map(member -> memberFactory.mapToResponse(member))
                .toList())
        .isActive(connection.isActive())
        .build();
  }
}
