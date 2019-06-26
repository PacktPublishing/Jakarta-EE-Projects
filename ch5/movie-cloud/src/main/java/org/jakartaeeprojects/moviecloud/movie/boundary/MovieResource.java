package org.jakartaeeprojects.moviecloud.movie.boundary;

import org.jakartaeeprojects.moviecloud.movie.control.RecommendationClient;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {
    @Inject
    private Logger logger;

    @Inject
    private MovieCatalog catalog;

    @Inject
    private RecommendationClient client;

    @GET
    public List<Movie> getMovies() {
        return catalog.list();
    }

    @GET
    @Path("/recommended")
    public List<Movie> getRecommended(@QueryParam("userId") long userId) {
        logger.log(Level.INFO, "Getting rec for user Id " + userId);
        List<Long> movieIds = this.client.getRecommendations(userId);
        logger.log(Level.INFO, "Found movie Ids " + movieIds);

        return catalog.list().stream()
                .filter(m -> movieIds.contains(m.getId()))
                .collect(toList());
    }

    @PUT
    @Path("/{movieId}")
    public void rate(@PathParam("movieId") long movieId, @QueryParam("userId") long userId,
                     @QueryParam("rating") int rating) {
        System.out.println("Movie " + movieId + ", with similar " + rating);
    }

}
