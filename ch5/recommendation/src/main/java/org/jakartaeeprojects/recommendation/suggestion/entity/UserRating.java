package org.jakartaeeprojects.recommendation.suggestion.entity;

import java.util.Objects;

public class UserRating {
    private int userId;
    private int movieId;
    private int rating;

    public UserRating() {
    }

    private UserRating(int userId, int movieId, int rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public static UserRating rate(int userId, int movieId, int rating) {
        return new UserRating(userId, movieId, rating);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
        UserRating that = (UserRating) o;
        return userId == that.userId &&
                movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }

    @Override
    public String toString() {
        return "UserRating{" +
                "userId=" + userId +
                ", movieId=" + movieId +
                ", rating=" + rating +
                '}';
    }
}
