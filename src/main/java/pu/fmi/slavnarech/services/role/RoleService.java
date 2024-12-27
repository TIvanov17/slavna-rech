package pu.fmi.slavnarech.services.role;

import pu.fmi.slavnarech.entities.role.Role;
import pu.fmi.slavnarech.entities.role.RoleName;

public interface RoleService {

  Role getByName(RoleName roleName);
}
