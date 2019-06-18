package org.jakartaeeprojects.moviecloud.movie.control;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/suggestions")
@RegisterRestClient
@RegisterClientHeaders
public interface SuggestionResourceService {

    @GET
    @Path("/{userId}")
    List<Long> findSuggested(@PathParam("userId") long userId);
}

