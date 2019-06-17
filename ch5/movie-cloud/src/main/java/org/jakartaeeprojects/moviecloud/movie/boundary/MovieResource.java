package org.jakartaeeprojects.moviecloud.movie.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("ping")
public class MovieResource {

    @Inject
    @ConfigProperty(name = "message")
    String message;    

    @GET
    public String hello() {
        return this.message + " Jakarta EE with MicroProfile 2+!";
    }

}
