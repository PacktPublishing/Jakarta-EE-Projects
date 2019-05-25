package org.jakartaeeprojects.spares.config;

import org.jakartaeeprojects.spares.multitenancy.boundary.TenantContext;
import org.jakartaeeprojects.spares.multitenancy.entity.Tenant;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class TenantConfig implements Serializable {

    @Inject
    private ExternalContext externalContext;

    public String get(String property) {
        final Tenant tenant = TenantContext.getCurrentTenant();
        if (tenant == null) {
            return null;
        }
        return tenant.getProperty(property);
    }
}
