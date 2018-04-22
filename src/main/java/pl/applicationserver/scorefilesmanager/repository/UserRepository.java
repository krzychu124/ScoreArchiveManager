package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.User;
import pl.applicationserver.scorefilesmanager.domain.role.Role;
import pl.applicationserver.scorefilesmanager.dto.SimpleUserDTO;
import pl.applicationserver.scorefilesmanager.dto.UserInfoDTO;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUserName(String userName);
    List<User> findAllByRolesIsIn(List<Role> roles);
    List<User> findAllByRolesNotNull();
    List<UserInfoDTO> getAllByRolesNotNull();
    SimpleUserDTO getById(Long id);
}
