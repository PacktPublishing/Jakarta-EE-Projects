package org.jakartaeeprojects.spares.auth.boundary;

import org.jakartaeeprojects.spares.auth.entity.Role;
import org.jakartaeeprojects.spares.auth.entity.User;
import org.jakartaeeprojects.spares.auth.entity.UserPrincipal;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;

@Named
@SessionScoped
public class UserSession implements Serializable {

    @Inject
    private SecurityContext securityContext;
    private User currentUser;

    public boolean isPresent() {
        return getCurrentUser() != null;
    }

    public boolean hasRole(Role role) {
        return isPresent() && currentUser.getRoles().contains(role);
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = securityContext.getPrincipalsByType(UserPrincipal.class)
                    .stream().map(UserPrincipal::getUser)
                    .findAny().orElse(null);
        }
        return currentUser;
    }
}
