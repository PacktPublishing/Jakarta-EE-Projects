package org.jakartaeeprojects.moviecloud.movie.boundary;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jakartaeeprojects.moviecloud.movie.control.SuggestionResourceService;
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
    private MovieCatalog catalog;

    @Inject
    private Logger logger;

    @Inject
    @RestClient
    private SuggestionResourceService suggestionService;

    @GET
    public List<Movie> getMovies() {
        return catalog.list();
    }

    @GET
    @Path("/recommended")
    public List<Movie> getRecommended(@QueryParam("userId") long userId) {
        List<Long> movieIds = getSuggested(userId);
        logger.log(Level.INFO, "Found movie Ids " + movieIds);

        return catalog.list().stream()
                .filter(m -> movieIds.contains(m.getId()))
                .collect(toList());
    }

    @Fallback(fallbackMethod = "fallbackForRecommendationService")
    public List<Long> getSuggested(long userId) {
        logger.log(Level.INFO, "invoking the recommendations service");
        if(userId == 13) {
            throw new IllegalStateException("13 not allowed");
        }

        List<Long> items = this.suggestionService.findSuggested(userId);
        logger.log(Level.INFO, "result of recommendations service " + items);
        return items;
    }

    public List<Long> fallbackForRecommendationService(long userId) {
        logger.log(Level.INFO, "using the fallback");
        return catalog.defaultRecommendation();
    }

    @PUT
    @Path("/{movieId}")
    public void rate(@PathParam("movieId") long movieId, @QueryParam("userId") long userId,
                     @QueryParam("rating") int rating) {
        System.out.println("Movie " + movieId + ", with similar " + rating);
    }

}
