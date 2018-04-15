package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.applicationserver.scorefilesmanager.domain.role.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege getByName(String name);
}
