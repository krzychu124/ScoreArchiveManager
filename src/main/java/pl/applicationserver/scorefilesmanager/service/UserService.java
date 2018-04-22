package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.domain.role.Privilege;
import pl.applicationserver.scorefilesmanager.domain.role.Role;
import pl.applicationserver.scorefilesmanager.dto.NewUser;

import java.util.List;

public interface UserService {

    boolean registerUser(NewUser newUser);
    List<Privilege> getPrivileges();
    List<Role> getRoles();
    Role addRole(String name);
    Privilege addPrivilege(String privilege);
    Role updateRole(Long roleId, Role newRole);
    boolean deleteRole(String roleName);
    boolean deletePrivilege(Long privilegeId);
    List getUserRoles();
}
