package org.jakartaeeprojects.spares.multitenancy.boundary;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.jakartaeeprojects.spares.multitenancy.entity.Tenant;

import static org.jakartaeeprojects.spares.multitenancy.boundary.TenantContext.DEFAULT_TENANT_SCHEMA;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        Tenant currentTenant = TenantContext.getCurrentTenant();
        return currentTenant != null ?
                currentTenant.getSchema() :
                DEFAULT_TENANT_SCHEMA;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
