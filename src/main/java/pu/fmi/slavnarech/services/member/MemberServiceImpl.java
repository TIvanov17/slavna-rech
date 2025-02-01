package pu.fmi.slavnarech.services.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pu.fmi.slavnarech.annotations.TransactionalService;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.dtos.MemberRequest;
import pu.fmi.slavnarech.entities.member.dtos.MemberResponse;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.role.RoleName;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.repositories.MemberRepository;
import pu.fmi.slavnarech.services.role.RoleService;

@TransactionalService
public class MemberServiceImpl implements MemberService {

  @Autowired private RoleService roleService;

  @Autowired private MemberRepository memberRepository;

  @Autowired private MemberFactory memberFactory;

  @Override
  public MemberResponse createMember(User user, Connection connection, Role role) {
    return null;
  }

  @Override
  public Member getByUserAndConnection(Long userId, Long connectionId) {
    return memberRepository.findByUserIdAndConnectionId(userId, connectionId);
  }

  @Override
  public MemberResponse getMember(Long userId, Long connectionId) {
    return memberFactory.mapToResponse(memberRepository.findByUserIdAndConnectionId(userId, connectionId));
  }

  @Override
  public void updateMemberRole(MemberRequest memberRequest) {
    Member member = getByUserAndConnection(memberRequest.getUserId(), memberRequest.getConnectionId());
    Role role = roleService.getByName(memberRequest.getRoleName());
    member.setRole(role);
    memberRepository.save(member);
  }
}
