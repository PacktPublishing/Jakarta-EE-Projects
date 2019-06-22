package org.jakartaeeprojects.moviecloud.movie.boundary;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jakartaeeprojects.moviecloud.movie.control.SuggestionResourceService;
import org.jakartaeeprojects.moviecloud.movie.control.UserRatingResourceService;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieCatalog catalog;

    @Inject
    @RestClient
    private SuggestionResourceService suggestionService;

    @Inject
    @RestClient
    private UserRatingResourceService ratingService;

    @GET
    public List<Movie> getMovies() {
        return catalog.list();
    }

    @GET
    @Path("/recommended")
    public List<Movie> getRecommended(@QueryParam("userId") long userId) {
        List<Long> movieIds = this.suggestionService.findSuggested(userId);
        System.out.println("Got back");
        movieIds.forEach(System.out::println);

        if(movieIds.isEmpty()) {
            System.out.println("show all");
            return catalog.list();
        }
        return catalog.list().stream().filter(m -> movieIds.contains(m.getId())).collect(toList());
    }

    @PUT
    @Path("/{movieId}")
    public void rate(@PathParam("movieId") long movieId,@QueryParam("userId") long userId,
                     @QueryParam("rating") int rating) {
        System.out.println("Movie " + movieId + ", with similar " + rating);
        this.ratingService.rateMovie(userId, movieId, rating);
    }

}
