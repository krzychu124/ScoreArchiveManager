package pl.applicationserver.scorefilesmanager.domain.role;

import org.springframework.security.core.GrantedAuthority;

public class RegularUserRole implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_REGULAR_USER";
    }
}
