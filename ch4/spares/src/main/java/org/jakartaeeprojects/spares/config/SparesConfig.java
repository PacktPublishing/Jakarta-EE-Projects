package org.jakartaeeprojects.spares.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "",
                useForwardToLogin = false
        )
)
@ApplicationScoped
@FacesConfig(
        version = JSF_2_3
)
public class SparesConfig {

}
