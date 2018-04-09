package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.User;
import pl.applicationserver.scorefilesmanager.domain.role.Role;
import pl.applicationserver.scorefilesmanager.dto.NewUser;
import pl.applicationserver.scorefilesmanager.repository.RolesRepository;
import pl.applicationserver.scorefilesmanager.repository.UserRepository;
import pl.applicationserver.scorefilesmanager.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RolesRepository rolesRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
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
        Role adminRole = rolesRepository.getByRole("ROLE_ADMIN");
        user.getRoles().add(adminRole);
        user.enableUser();
        userRepository.save(user);
        return true;
    }
}
