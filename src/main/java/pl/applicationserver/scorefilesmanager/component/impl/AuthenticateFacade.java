package pl.applicationserver.scorefilesmanager.component.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.applicationserver.scorefilesmanager.component.IAuthenticationFacade;

@Component
public class AuthenticateFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
