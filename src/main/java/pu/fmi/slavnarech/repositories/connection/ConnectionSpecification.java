package pu.fmi.slavnarech.repositories.connection;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.ConnectionType;
import pu.fmi.slavnarech.entities.connection.Connection_;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.Member_;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.User_;

public class ConnectionSpecification {

  public static Specification<Connection> hasChannelsWithUserId(Long userId) {

    return ((root, query, criteriaBuilder) -> {
      Join<Connection, Member> member = root.join(Connection_.members);
      Join<Member, User> user = member.join(Member_.user);

      return criteriaBuilder.and(
          criteriaBuilder.isTrue(root.get(Connection_.isActive)),
          criteriaBuilder.equal(root.get(Connection_.connectionType), ConnectionType.CHANNEL),
          criteriaBuilder.equal(user.get(User_.id), userId));
    });
  }
}
