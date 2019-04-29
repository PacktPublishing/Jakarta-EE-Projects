package org.jakartaeeprojects.catalog.metric;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/metric")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class MetricController {

    @Inject
    @ConfigProperty(name = "page.limit", defaultValue = "10")
    private String pageLimit;

    @GET
    public Response getPageLimit() {
        return Response.ok(getPageLimitCount()).build();
    }

    @Gauge(name = "pageLimit", unit = MetricUnits.NONE, absolute = true)
    private String getPageLimitCount() {
        return pageLimit;
    }
}
