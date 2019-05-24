package org.jakartaeeprojects.spares.multitenancy.boundary;

import org.jakartaeeprojects.spares.multitenancy.entity.Tenant;

import java.util.HashMap;
import java.util.Map;

public class TenantContext {

    public static final String DEFAULT_TENANT_SCHEMA = "tenant-mgmt-sys";
    public static final ThreadLocal<Tenant> TENANT_IDENTIFIER =
            new ThreadLocal<>();
    private static final Map<String, Tenant> TENANTS = new HashMap<>();

    public static void reset() {
        TENANT_IDENTIFIER.remove();
    }

    public static Map<String, Tenant> getTenants() {
        return TENANTS;
    }

    public static Tenant getCurrentTenant() {
        return TENANT_IDENTIFIER.get();
    }

    public static void setCurrentTenant(Tenant tenant) {
        TENANT_IDENTIFIER.set(tenant);
    }
}
