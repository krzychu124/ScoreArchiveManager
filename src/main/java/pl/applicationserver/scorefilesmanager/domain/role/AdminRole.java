package pl.applicationserver.scorefilesmanager.domain.role;

public class AdminRole extends GrantedAuthorityImpl{
    public AdminRole() {
        super("ROLE_ADMIN");
    }
}
