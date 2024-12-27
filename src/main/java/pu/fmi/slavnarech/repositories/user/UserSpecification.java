package pu.fmi.slavnarech.repositories.user;

import org.springframework.data.jpa.domain.Specification;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.User_;

public class UserSpecification {

  public static Specification<User> buildPredicateForSearchByUsernameOrEmail(String username) {

    return ((root, query, criteriaBuilder) -> {
      String pattern = username != null ? "%" + username + "%" : "%%";
      return criteriaBuilder.and(
          criteriaBuilder.isTrue(root.get(User_.isActive)),
          criteriaBuilder.or(
              criteriaBuilder.like(root.get(User_.username), pattern),
              criteriaBuilder.like(root.get(User_.email), pattern)));
    });
  }
}
