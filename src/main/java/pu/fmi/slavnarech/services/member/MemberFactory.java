package pu.fmi.slavnarech.services.member;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.MemberId;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.member.dtos.MemberResponse;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.user.User;

@Component
public class MemberFactory {

  public Member mapToEntity(
      User user, Connection connection, Role role, MemberStatus memberStatus) {
    return Member.builder()
        .id(new MemberId(user.getId(), connection.getId()))
        .user(user)
        .connection(connection)
        .joinDate(LocalDate.now())
        .role(role)
        .status(memberStatus)
        .isActive(Boolean.TRUE)
        .build();
  }

  public MemberResponse mapToResponse(Member member) {
    User user = member.getUser();
    return MemberResponse.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .joinDate(member.getJoinDate())
        .isActive(member.isActive())
        .role(member.getRole().getName())
        .status(member.getStatus())
        .build();
  }
}
