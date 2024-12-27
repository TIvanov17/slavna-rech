package pu.fmi.slavnarech.services.member;

import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.member.dtos.MemberResponse;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.user.User;

public interface MemberService {

  MemberResponse createMember(User user, Connection connection, Role role);
}
