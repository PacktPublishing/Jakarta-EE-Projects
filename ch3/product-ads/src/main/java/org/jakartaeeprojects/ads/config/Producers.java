
package org.jakartaeeprojects.ads.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class Producers {

    @Produces
    public Logger loggerExposer(final InjectionPoint ip) {
        return getLogger(ip.getMember()
                           .getDeclaringClass()
                           .getName());
    }

    @Produces
    public EntityManager createEntityManager() {
        return Persistence
                .createEntityManagerFactory("adsPU")
                .createEntityManager();
    }
}
