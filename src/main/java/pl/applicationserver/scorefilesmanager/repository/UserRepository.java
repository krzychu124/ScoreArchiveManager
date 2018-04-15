package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.User;
import pl.applicationserver.scorefilesmanager.domain.role.Role;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUserName(String userName);
    List<User> findAllByRolesIsIn(List<Role> roles);
}
