package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.applicationserver.scorefilesmanager.domain.role.Role;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Role getByRole(String role);
}
