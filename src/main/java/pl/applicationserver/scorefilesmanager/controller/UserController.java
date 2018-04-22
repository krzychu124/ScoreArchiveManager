package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.applicationserver.scorefilesmanager.domain.role.Privilege;
import pl.applicationserver.scorefilesmanager.domain.role.Role;
import pl.applicationserver.scorefilesmanager.domain.view.UserRoleView;
import pl.applicationserver.scorefilesmanager.dto.NewUser;
import pl.applicationserver.scorefilesmanager.service.UserService;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody NewUser newUser) {
        boolean registered = userService.registerUser(newUser);
        if (registered) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/me")
    public Principal getUser(Principal user) {
        return user;
    }

    @GetMapping("/privileges")
    public ResponseEntity<List<Privilege>> getPrivileges() {
        return new ResponseEntity<>(userService.getPrivileges(), OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(userService.getRoles(), OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return new ResponseEntity<>(userService.addRole(role.getRole()), CREATED);
    }

    @PostMapping("/privileges")
    public ResponseEntity<Privilege> createPrivilege(@RequestBody Privilege privilege) {
        return new ResponseEntity<>(userService.addPrivilege(privilege.getName()), CREATED);
    }

    @PutMapping("roles/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable("roleId") Long roleId, @RequestBody Role newRole) {
        return new ResponseEntity<>(userService.updateRole(roleId, newRole), OK);
    }

    @DeleteMapping("/privileges/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        boolean deleted = userService.deletePrivilege(id);
        if(deleted) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(BAD_REQUEST);
    }

    @GetMapping("/userRoles")
    public ResponseEntity<List<UserRoleView>> getUserRoles(){
        return new ResponseEntity<>(userService.getUserRoles(),OK);
    }
}
