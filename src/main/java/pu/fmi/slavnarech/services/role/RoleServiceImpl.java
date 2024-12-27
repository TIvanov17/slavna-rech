package pu.fmi.slavnarech.services.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.role.RoleName;
import pu.fmi.slavnarech.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired private RoleRepository roleRepository;

  @Override
  public Role getByName(RoleName roleName) {
    return roleRepository.findByName(roleName);
  }
}
