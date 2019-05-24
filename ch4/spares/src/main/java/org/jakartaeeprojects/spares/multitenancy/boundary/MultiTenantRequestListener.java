package org.jakartaeeprojects.spares.multitenancy.boundary;

import org.jakartaeeprojects.spares.multitenancy.entity.Tenant;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener()
public class MultiTenantRequestListener implements ServletRequestListener {
    private static final Logger logger = Logger.getLogger(MultiTenantRequestListener.class.getName());

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        final HttpServletRequest request = (HttpServletRequest) event.getServletRequest();

        TenantContext.reset();
    }

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        final HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        final String tenantId = request.getHeader("X-Tenant-ID");
        logger.log(Level.INFO, "Request X-Tenant-ID is {0}", tenantId);

        loadTenant(tenantId);
    }

    private void loadTenant(String tenantId) {
        Tenant resolvedTenant = TenantContext.getTenants().get(tenantId);
        TenantContext.setCurrentTenant(resolvedTenant);

        logger.log(Level.INFO, "Tenant found {0}", resolvedTenant);
    }

}
