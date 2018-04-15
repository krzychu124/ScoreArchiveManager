package pl.applicationserver.scorefilesmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class AppUserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findOneByUserName(s);
    }
}
