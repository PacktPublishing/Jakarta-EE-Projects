package org.jakartaeeprojects.moviecloud.movie.entity;

import java.util.Objects;
import java.util.Random;

public class Movie {
    private int id;
    private String name;
    private String plot;
    private Genre genre;
    private int rating;

    public Movie() {
    }

    public Movie(int id, String name, Genre genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.rating = new Random().nextInt(5) + 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", plot='" + plot + '\'' +
                ", genre=" + genre +
                ", rating=" + rating +
                '}';
    }
}
