package pu.fmi.slavnarech.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pu.fmi.slavnarech.annotations.ValidRestApi;
import pu.fmi.slavnarech.entities.member.dtos.MemberRequest;
import pu.fmi.slavnarech.entities.member.dtos.MemberResponse;
import pu.fmi.slavnarech.services.member.MemberService;

@ValidRestApi("api/v1/members")
public class MemberApi {

  @Autowired private MemberService memberService;

  @GetMapping("/users/{userId}/connections/{connectionId}")
  public ResponseEntity<MemberResponse> getMember(@PathVariable Long userId, @PathVariable Long connectionId){
    return ResponseEntity.ok(memberService.getMember(userId, connectionId));
  }

  @PutMapping("/role")
  public ResponseEntity<Void> updateRole(@RequestBody MemberRequest memberRequest){
    memberService.updateMemberRole(memberRequest);
    return ResponseEntity.ok().build();
  }
}
