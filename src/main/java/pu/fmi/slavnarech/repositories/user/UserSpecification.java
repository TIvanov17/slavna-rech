package pu.fmi.slavnarech.repositories.user;

import org.springframework.data.jpa.domain.Specification;
import pu.fmi.slavnarech.entities.user.User;

public class UserSpecification {

  public static Specification<User> buildPredicateForSearchByUsernameOrEmail(String username) {

    return ((root, query, criteriaBuilder) -> {
      String pattern = username != null ? "%" + username + "%" : "%%";
      return criteriaBuilder.or(
          criteriaBuilder.like(root.get("username"), pattern),
          criteriaBuilder.like(root.get("email"), pattern));
    });
  }
}
