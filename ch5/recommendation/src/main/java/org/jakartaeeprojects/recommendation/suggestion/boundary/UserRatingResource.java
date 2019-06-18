package org.jakartaeeprojects.recommendation.suggestion.boundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("ratings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRatingResource {

    @Inject
    private MovieManager movieManager;

    @PUT
    public Response rateMovie(@QueryParam("userId") long userId, 
                              @QueryParam("movieId") long movieId,
                              @QueryParam("rating") long movieId) {
        this.movieManager.addUserRating(movieId, userId, rating);
        return Response.ok().build();                      
    }
}