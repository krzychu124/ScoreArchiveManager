package pl.applicationserver.scorefilesmanager.component;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
