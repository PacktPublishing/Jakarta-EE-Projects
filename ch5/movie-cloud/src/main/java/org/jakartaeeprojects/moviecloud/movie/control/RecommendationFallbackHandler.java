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
    Logger logger;

    @Inject
    MovieCatalog catalog;

    @Override
    public List<Movie> handle(final ExecutionContext context) {
        logger.log(Level.INFO, "=================  FALLBACK ================= ");
        return catalog.topRatedMovies(4);
    }
}
