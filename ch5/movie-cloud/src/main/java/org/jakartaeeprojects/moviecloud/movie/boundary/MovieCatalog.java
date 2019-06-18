package org.jakartaeeprojects.moviecloud.movie.boundary;

import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class MovieCatalog {

    private List<Movie> movieList;

    @PostConstruct
    private void init() {
        this.movieList = Arrays.asList(
                new Movie(150, "Candy"),
                new Movie(160, "Drink"),
                new Movie(170, "Soda"),
                new Movie(180, "Popcorn"),
                new Movie(190, "Snacks"),
                new Movie(200, "Doghnut"),
                new Movie(220, "Doghnut"),
                new Movie(230, "Doghnut"),
                new Movie(240, "Doghnut"),
                new Movie(250, "Doghnut"),
                new Movie(260, "Doghnut")
        );
    }

    public List<Movie> list() {
        return this.movieList;
    }
}
