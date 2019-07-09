package org.jakartaeeprojects.moviecloud.movie.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class MovieCatalog {

    @Inject
    Logger logger;

    private List<Movie> movieList;

    @Inject
    @ConfigProperty(name = "movie.limit", defaultValue = "5")
    Integer limit;

    @PostConstruct
    public void init() {
        this.movieList = Arrays.asList(
                new Movie(1, 1999, "The Matrix"),
                new Movie(2, 1985, "Back to the Future"),
                new Movie(3, 2018, "The Predator"),
                new Movie(4, 1999, "The Mummy"),
                new Movie(5, 2006, "Cars"),
                new Movie(6, 2017, "Coco"),
                new Movie(7, 2001, "Shrek"),
                new Movie(8, 2017, "Ferdinand"),
                new Movie(9, 2017, "Jumanji: Welcome To The Jungle"),
                new Movie(10, 1984, "The Terminator")
        );
        logger.log(Level.INFO, "=============== init movies data ======================");
        this.movieList.sort(comparing(Movie::getRating).reversed());
        this.movieList.forEach(System.out::println);
    }

    public List<Movie> list() {
        return topRatedMovies(this.limit);
    }

    public List<Movie> topRatedMovies(int max) {
        return this.movieList.stream()
                .sorted(comparing(Movie::getRating).reversed())
                .limit(max)
                .collect(toList());
    }

    public Optional<Movie> find(int movieId) {
        return this.movieList.stream()
                .filter(m -> m.getId() == movieId)
                .findAny();
    }

    public List<Movie> getMoviesBy(final List<Integer> movieIds) {
        logger.log(Level.INFO, "Looking up " + movieIds);
        return this.movieList.stream()
                .filter(m -> movieIds.contains(m.getId()))
                .sorted(comparing(Movie::getRating).reversed())
                .collect(toList());
    }


}
