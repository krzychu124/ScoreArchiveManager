package pl.applicationserver.scorefilesmanager.domain.role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sam_role")
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role", unique = true)
    private String role;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sam_roles_privileges",
            joinColumns = @JoinColumn(
                    name = "sam_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "sam_privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges = new HashSet<>();

    public Role() {

    }

    public Role(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }
}
