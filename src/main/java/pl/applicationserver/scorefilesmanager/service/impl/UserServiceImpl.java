package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.User;
import pl.applicationserver.scorefilesmanager.domain.role.Privilege;
import pl.applicationserver.scorefilesmanager.domain.role.Role;
import pl.applicationserver.scorefilesmanager.dto.NewUser;
import pl.applicationserver.scorefilesmanager.repository.PrivilegeRepository;
import pl.applicationserver.scorefilesmanager.repository.RolesRepository;
import pl.applicationserver.scorefilesmanager.repository.UserRepository;
import pl.applicationserver.scorefilesmanager.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RolesRepository rolesRepository;
    private PrivilegeRepository privilegeRepository;
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository, PrivilegeRepository privilegeRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.privilegeRepository = privilegeRepository;
        this.entityManager = entityManager;
    }

    @Override
    public boolean registerUser(NewUser newUser) {
        String hashedPass = passwordEncoder.encode(newUser.getPassword());
        User exists = userRepository.findOneByUserName(newUser.getUserName());
        if(exists !=null) {
            return false;
        }
        User user = new User();
        user.setName(newUser.getName());
        user.setSurname(newUser.getSurname());
        user.setUserName(newUser.getUserName());
        user.setEmail(newUser.getEmail());
        user.setPassword(hashedPass);
        Role adminRole = rolesRepository.getByRole("ROLE_ANONYMOUS");
        user.getRoles().add(adminRole);
        user.enableUser();
        userRepository.save(user);
        return true;
    }

    @Override
    public List<Privilege> getPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Role addRole(String name) {
        String newRoleName = name.replace("ROLE_", "");
        if(rolesRepository.getByRole("ROLE_" +newRoleName) == null) {
            Role role = new Role();
            role.setRole("ROLE_" + name);
            return rolesRepository.save(role);
        }
        return null;
    }

    @Override
    public Privilege addPrivilege(String privilege) {
        if(privilegeRepository.getByName(privilege) == null) {
            Privilege newPrivilege = new Privilege();
            newPrivilege.setName(privilege);
            return privilegeRepository.save(newPrivilege);
        }
        return null;
    }

    @Override
    public Role updateRole(Long roleId, Role newRole) {
        try {
            Role role = rolesRepository.getOne(roleId);
            Set<Privilege> uniquePrivileges = new HashSet<>();
            uniquePrivileges.addAll(role.getPrivileges());
            uniquePrivileges.addAll(newRole.getPrivileges());
            role.setPrivileges(new HashSet<>(uniquePrivileges));
            return rolesRepository.save(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteRole(String roleName) {
        return false;
    }

    @Override
    public boolean deletePrivilege(Long privilegeId) {
        if(privilegeRepository.existsById(privilegeId)) {
            privilegeRepository.deleteById(privilegeId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List getUserRoles() {
        Query q = entityManager.createNativeQuery("");
        return userRepository.getAllByRolesNotNull();
    }
}
