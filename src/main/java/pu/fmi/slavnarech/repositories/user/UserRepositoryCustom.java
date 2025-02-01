package pu.fmi.slavnarech.repositories.user;

import org.springframework.data.domain.Page;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.user.dtos.UserConnectionResponse;
import pu.fmi.slavnarech.utils.PageFilter;

public interface UserRepositoryCustom {

  Page<UserConnectionResponse> getFriendInvitesFor(Long id, PageFilter pageFilter);

  Page<UserConnectionResponse> getFriendsOfUser(Long id, PageFilter pageFilter);

  Page<Connection> getChannels(Long id, PageFilter pageFilter);
}
