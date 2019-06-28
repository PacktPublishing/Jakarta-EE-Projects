package org.jakartaeeprojects.moviecloud.movie.boundary;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jakartaeeprojects.moviecloud.movie.control.RecommendationClient;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieCatalog catalog;

    @Inject
    private RecommendationClient client;

    @GET
    public List<Movie> getMovies() {
        return catalog.list();
    }

    @Fallback(fallbackMethod = "fallback")
    @Timeout(500)
    @Path("/fall")
    @GET
    public String checkTimeout() {
        try {
            Thread.sleep(700L);
        } catch (InterruptedException e) {
            //
        }
        return "Never from normal processing";
    }

    public String fallback() {
        return "Fallback answer due to timeout";
    }


    @GET
    @Path("/{movieId}")
    public Response getMovie(@PathParam("movieId") int movieId) {
        Optional<Movie> movie = catalog.find(movieId);
        if (movie.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
        return Response.ok(movie.get())
                .build();
    }

    @GET
    @Path("/recommend/{userId}")
    public List<Movie> getRecommended(@PathParam("userId") int userId) {
        List<Movie> suggested = this.client.getRecommendations(userId);
        System.out.println("Suggested " + suggested);
        return suggested;
    }
}
