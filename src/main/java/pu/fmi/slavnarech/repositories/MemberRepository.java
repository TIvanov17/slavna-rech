package pu.fmi.slavnarech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.slavnarech.entities.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {}
