package pu.fmi.slavnarech.repositories.user;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.dtos.UserConnectionResponse;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>,
    UserRepositoryCustom {

  User findByEmail(String email);

  User findByUsername(String username);

}
