package pu.fmi.slavnarech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.role.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(RoleName roleName);
}
