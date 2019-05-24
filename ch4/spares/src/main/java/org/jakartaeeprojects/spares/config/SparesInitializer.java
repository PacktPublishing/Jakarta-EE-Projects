package org.jakartaeeprojects.spares.config;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jakartaeeprojects.spares.multitenancy.boundary.SchemaConnectionProvider;
import org.jakartaeeprojects.spares.multitenancy.entity.Tenant;
import org.jakartaeeprojects.spares.multitenancy.boundary.TenantContext;

@Startup
@Singleton
public class SparesInitializer {

    private static final Logger logger = Logger.getLogger(SparesInitializer.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private SchemaConnectionProvider connectionProvider;

    @PostConstruct
    public void onStartup() {
        registerTenants();
    }

    private void registerTenants() {
        try {
            List<Tenant> tenants = em.createQuery("FROM Tenant t", Tenant.class)
                    .getResultList();
            logger.log(Level.INFO, "Found {0} tenants", tenants.size());
            tenants.forEach(this::addTenant);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong with adding tenants", e);
        }
    }

    private void addTenant(Tenant tenant) {
        logger.log(Level.INFO, "Adding tenant {0}", tenant.toString());

        TenantContext.getTenants().put(tenant.getId(), tenant);
        connectionProvider.addTenantConnectionProvider(tenant.getSchema());
    }
}
