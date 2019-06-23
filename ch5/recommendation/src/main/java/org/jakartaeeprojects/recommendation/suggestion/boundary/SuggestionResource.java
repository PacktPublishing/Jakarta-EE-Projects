package org.jakartaeeprojects.recommendation.suggestion.boundary;

import org.jakartaeeprojects.recommendation.suggestion.control.SuggestionGenerator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("suggestions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuggestionResource {

    @Inject
    private SuggestionGenerator generator;

    @GET
    @Path("/{userId}")
    public List<Long> findSuggested(@PathParam("userId") long userId) {
//        try {
            throw new RuntimeException("Fault injected");
//            return generator.suggestMoviesForUser(userId);
//        } catch (Exception e) {
//            return Collections.EMPTY_LIST;
//        }
    }
}
