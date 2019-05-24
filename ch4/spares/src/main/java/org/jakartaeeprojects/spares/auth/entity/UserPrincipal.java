package org.jakartaeeprojects.spares.auth.entity;

import javax.security.enterprise.CallerPrincipal;

public class UserPrincipal extends CallerPrincipal {

    private final User user;

    public UserPrincipal(User user) {
        super(user.getEmail());
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
