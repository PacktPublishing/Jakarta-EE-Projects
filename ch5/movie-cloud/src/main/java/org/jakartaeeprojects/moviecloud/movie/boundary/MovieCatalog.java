package org.jakartaeeprojects.moviecloud.movie.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jakartaeeprojects.moviecloud.movie.entity.Genre;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class MovieCatalog {

    private List<Movie> movieList;

    @Inject
    @ConfigProperty(name = "movie.limit", defaultValue = "10")
    private Integer limit;

    @PostConstruct
    public void init() {
        this.movieList = Arrays.asList(
                new Movie(1, "Matrix", Genre.SciFi),
                new Movie(2, "Back to the Future", Genre.SciFi),
                new Movie(3, "Predator", Genre.SciFi),
                new Movie(4, "Jungle Book", Genre.Cartoon),
                new Movie(5, "Lion King", Genre.Cartoon),
                new Movie(6, "Coco", Genre.Cartoon),
                new Movie(7, "Shrek", Genre.Cartoon),
                new Movie(8, "Kung Fu Panda", Genre.Cartoon),
                new Movie(9, "The Martian", Genre.SciFi),
                new Movie(10, "Inception", Genre.SciFi)
        );
        System.out.println("=============== init movies start ======================");
        this.movieList.sort(comparing(Movie::getRating).reversed());
        this.movieList.forEach(System.out::println);
        System.out.println("=============== init movies ends =======================");
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
                .findFirst();
    }

    private List<Movie> topRatedMoviesByGenre(Genre genre, int max) {
        return this.movieList.stream()
                .filter(m -> m.getGenre() == genre)
                .sorted(comparing(Movie::getRating).reversed())
                .limit(max)
                .collect(toList());
    }

    public List<Movie> getMoviesBy(final List<Integer> movieIds) {
        System.out.println("Looking up " + movieIds);
        return this.movieList.stream()
                .filter(m -> movieIds.contains(m.getId()))
                .sorted(comparing(Movie::getRating).reversed())
                .collect(toList());
    }


}
