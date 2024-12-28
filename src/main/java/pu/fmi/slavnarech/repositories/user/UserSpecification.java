package pu.fmi.slavnarech.repositories.user;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.Connection_;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.member.Member_;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.User_;

public class UserSpecification {

  public static Specification<User> searchByUsernameOrEmail(String username) {
    return ((root, query, criteriaBuilder) -> {
      String pattern = username != null ? "%" + username + "%" : "%%";
      return criteriaBuilder.and(
          criteriaBuilder.isTrue(root.get(User_.isActive)),
          criteriaBuilder.or(
              criteriaBuilder.like(root.get(User_.username), pattern),
              criteriaBuilder.like(root.get(User_.email), pattern)));
    });
  }

  public static Specification<User> hasFriends(Long id) {

    return ((root, query, criteriaBuilder) -> {
      query.distinct(true);

      Join<User, Member> member = root.join(User_.members);
      Join<Member, Connection> connection = member.join(Member_.connection);

      Subquery<Long> subquery = query.subquery(Long.class);
      Root<Member> memberSubquery = subquery.from(Member.class);
      Join<Member, Connection> connectionSubquery = memberSubquery.join(Member_.connection);
      Join<Member, User> memberUser = memberSubquery.join(Member_.user);

      subquery.select(memberUser.get(User_.id));
      subquery.where(
          criteriaBuilder.equal(memberSubquery.get(Member_.status), MemberStatus.ACCEPTED),
          criteriaBuilder.equal(
              connectionSubquery.get(Connection_.id), connection.get(Connection_.id)),
          criteriaBuilder.notEqual(memberUser.get(User_.id), id),
          criteriaBuilder.isTrue(connectionSubquery.get(Connection_.isActive)));

      return criteriaBuilder.and(
          criteriaBuilder.in(root.get(User_.id)).value(subquery),
          criteriaBuilder.notEqual(root.get(User_.id), id));
    });
  }
}
