package pl.applicationserver.scorefilesmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.domain.User;
import pl.applicationserver.scorefilesmanager.repository.UserRepository;
import pl.applicationserver.scorefilesmanager.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }
}
