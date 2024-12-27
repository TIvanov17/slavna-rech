package pu.fmi.slavnarech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.slavnarech.entities.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {}
