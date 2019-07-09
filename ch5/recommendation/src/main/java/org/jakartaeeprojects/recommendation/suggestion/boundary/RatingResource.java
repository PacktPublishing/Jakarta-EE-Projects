package org.jakartaeeprojects.recommendation.suggestion.boundary;

import org.jakartaeeprojects.recommendation.suggestion.entity.UserRating;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ratings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingResource {

    @Inject
    RatingManager ratingManager;

    @GET
    public Response get() {
        return Response.ok(this.ratingManager.getRatingsMap()).build();
    }

    @PUT
    public Response rateMovie(UserRating rating) {
        this.ratingManager.addOrUpdateUserRating(rating);
        return Response.ok().build();
    }
}
