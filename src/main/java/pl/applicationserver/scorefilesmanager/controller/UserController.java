package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.applicationserver.scorefilesmanager.dto.NewUser;
import pl.applicationserver.scorefilesmanager.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(name = "/register")
    public ResponseEntity<String> registerUser(@RequestBody NewUser newUser) {
        boolean registered = userService.registerUser(newUser);
        if (registered) {
            return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Registration failed", HttpStatus.BAD_REQUEST);
    }
}
