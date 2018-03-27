package pl.applicationserver.scorefilesmanager.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.applicationserver.scorefilesmanager.domain.role.GrantedAuthorityImpl;
import pl.applicationserver.scorefilesmanager.domain.role.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "archive_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String userName;
    @NotNull
    private String email;
    @NotNull
    private String passHash;
    @NotNull
    private String passSalt;
    private boolean isNonExpired;
    private boolean isNonLocked;
    private boolean isEnabled = true;
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new GrantedAuthorityImpl(role.getRole())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return passSalt + passHash;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHashedPassword(String passHash, String passSalt) {
        this.passHash = passHash;
        this.passSalt = passSalt;
    }

    public void disableUser() {
        isEnabled = false;
    }
}
