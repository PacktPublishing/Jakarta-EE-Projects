package org.jakartaeeprojects.recommendation.suggestion.boundary;

import org.jakartaeeprojects.recommendation.suggestion.control.SuggestionGenerator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("suggestions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuggestionResource {
    @Inject
    private Logger logger;

    @Inject
    private SuggestionGenerator generator;

    @GET
    @Path("/{userId}")
    public List<Integer> findSuggested(@PathParam("userId") int userId) {
        try {
            logger.log(Level.INFO,"Getting suggestions for user " + userId);
            List<Integer> movies = generator.suggestMoviesForUser(userId);
            logger.log(Level.INFO,"Recommending " + movies);
            return movies;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get suggestions for user " + userId, e);
            return Collections.EMPTY_LIST;
        }
    }
}
