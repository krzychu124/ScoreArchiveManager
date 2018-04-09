package pl.applicationserver.scorefilesmanager.service;

import pl.applicationserver.scorefilesmanager.dto.NewUser;

public interface UserService {

    boolean registerUser(NewUser newUser);
}
