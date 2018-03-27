package pl.applicationserver.scorefilesmanager.domain.role;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {
    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority =authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
