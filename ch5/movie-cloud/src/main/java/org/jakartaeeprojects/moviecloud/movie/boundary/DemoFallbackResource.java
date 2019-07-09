package org.jakartaeeprojects.moviecloud.movie.boundary;

import org.jakartaeeprojects.moviecloud.movie.control.DemoClient;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("faults")
public class DemoFallbackResource {

    @Inject
    DemoClient client;

    @Path("timeout")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String fallbackTest() {
        return client.longRunningOperation();
    }

    @Path("circuit-breaker")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movie> circuitBreakerTest() {
        List<Movie> results = client.externalServiceCall();

        return results;
    }

}
