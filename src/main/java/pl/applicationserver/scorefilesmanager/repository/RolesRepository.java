package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.applicationserver.scorefilesmanager.domain.role.Privilege;
import pl.applicationserver.scorefilesmanager.domain.role.Role;

import java.util.List;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Role getByRole(String role);
    List<Role> getAllByPrivilegesIn(List<Privilege> privileges);
}
