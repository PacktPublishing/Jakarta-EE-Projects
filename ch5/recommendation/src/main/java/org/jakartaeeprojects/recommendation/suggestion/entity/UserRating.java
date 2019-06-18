package org.jakartaeeprojects.recommendation.suggestion.entity;

import java.util.Objects;

public class UserRating {
    private long userId;
    private long movieId;
    private int rating;

    public UserRating(long userId, long movieId, int rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
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
