package org.jakartaeeprojects.moviecloud.movie.control;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.jakartaeeprojects.moviecloud.movie.boundary.MovieCatalog;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecommendationFallbackHandler implements FallbackHandler<List<Movie>> {

    @Inject
    private Logger logger;

    @Inject
    private MovieCatalog catalog;

    @Override
    public List<Movie> handle(final ExecutionContext context) {
        logger.log(Level.INFO, "Recommendation fallback");
        return catalog.topRatedMovies(4);
    }
}
