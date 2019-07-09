package org.jakartaeeprojects.moviecloud.movie.control;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jakartaeeprojects.moviecloud.movie.boundary.MovieCatalog;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RecommendationClient {

    @Inject
    @ConfigProperty(name = "recommendation.service")
    private String baseUrl;

    @Inject
    private Logger logger;

    @Inject
    private MovieCatalog catalog;

    private Client client;
    private WebTarget target;

    @PostConstruct
    public void initClient() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(800, TimeUnit.MILLISECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();
        target = client.target(baseUrl + "/resources/suggestions/{id}");
    }

    //After a delay of 1 sec a timeout occurs resulting in TimeoutException
    @Fallback(fallbackMethod = "getDefaultRecommendations")
    @Timeout
    public List<Movie> getRecommendations(int id) {
        List<Integer> movieIds = callRecommendationService(id);
        if (movieIds.isEmpty()) {
            logger.log(Level.INFO, "No recommendations found for user " + id);
            throw new IllegalStateException("No recommendations found");
        }

        return catalog.getMoviesBy(movieIds);
    }

    private List<Integer> callRecommendationService(int id) {
        return target.resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Integer>>() {
                });
    }

    public List<Movie> getDefaultRecommendations(int id) {
        logger.log(Level.INFO, "using the default recommendation fallback");
        return catalog.topRatedMovies(3);
    }

    @PreDestroy
    public void closeClient() {
        client.close();
    }

}