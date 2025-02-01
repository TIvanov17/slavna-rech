package pu.fmi.slavnarech.services.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.dtos.MemberResponse;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.repositories.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Override
  public MemberResponse createMember(User user, Connection connection, Role role) {
    return null;
  }

  @Override
  public Member getByUserAndConnection(Long userId, Long connectionId) {
    return memberRepository.findByUserIdAndConnectionId(userId, connectionId);
  }
}
