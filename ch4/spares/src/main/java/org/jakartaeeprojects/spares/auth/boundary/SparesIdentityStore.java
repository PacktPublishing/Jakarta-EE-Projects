package org.jakartaeeprojects.spares.auth.boundary;

import org.jakartaeeprojects.spares.auth.control.UserNotFoundException;
import org.jakartaeeprojects.spares.auth.entity.User;
import org.jakartaeeprojects.spares.auth.entity.UserPrincipal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Optional;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class SparesIdentityStore implements IdentityStore {

    @Inject
    private UserService service;

    @Override
    public CredentialValidationResult validate(
            Credential credential) {

        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
        String email = login.getCaller();
        String password = login.getPasswordAsString();

        Optional<User> optionalUser = findUser(email, password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new CredentialValidationResult(new UserPrincipal(user),
                    user.getRolesAsStrings());
        }
        return INVALID_RESULT;
    }

    private Optional<User> findUser(String email, String password) {
        try {
            return Optional.of(service.findByCredentials(email, password));
        } catch (UserNotFoundException e) {
            return Optional.empty();
        }
    }

}
