package org.jakartaeeprojects.spares.view.auth;

import org.jakartaeeprojects.spares.auth.boundary.UserSession;
import org.jakartaeeprojects.spares.auth.entity.User;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

import static javax.security.enterprise.AuthenticationStatus.*;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import static org.omnifaces.util.Faces.redirect;

@Named
@RequestScoped
public class LoginBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    private User user;

    @Inject
    private UserSession currentUser;

    @PostConstruct
    private void init() {
        if (currentUser.isPresent()) {
            redirect("app/parts.xhtml");
        } else {
            user = new User();
        }
    }

    public void submit() throws IOException {
        Credential credential = new UsernamePasswordCredential(user.getEmail(), new Password(user.getPassword()));

        AuthenticationStatus status = securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                withParams().credential(credential));
        if (status.equals(SEND_CONTINUE)) {
            facesContext.responseComplete();
        } else if (status.equals(SEND_FAILURE)) {
            Messages.addGlobalError("Login failed");
        } else if (status.equals(SUCCESS)) {
            redirect("/app/parts.xhtml");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
