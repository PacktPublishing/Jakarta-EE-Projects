package org.jakartaeeprojects.recommendation.suggestion.control;

import org.jakartaeeprojects.recommendation.suggestion.boundary.RatingManager;
import org.jakartaeeprojects.recommendation.suggestion.entity.UserRating;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Dependent
public class SimpleSuggestionGenerator implements SuggestionGenerator {
    @Inject
    Logger logger;

    @Inject
    RatingManager ratingManager;

    @Override
    public List<Integer> suggestMoviesForUser(int userId) {
        Set<UserRating> userRatedMovies = ratingManager.getRatings().stream()
                .filter(userRating -> userRating.getUserId() == userId)
                .collect(toSet());
        logger.log(Level.INFO, "User rated these movies");
        userRatedMovies.stream().map(UserRating::getMovieId).forEach(System.out::println);

        Set<Integer> similarUsers = usersWithSimilarInterests(userId, userRatedMovies);
        logger.log(Level.INFO, "Similar users " + similarUsers);

        return unRatedButLikedBySimilarUsers(userRatedMovies, similarUsers);
    }

    private List<Integer> unRatedButLikedBySimilarUsers(Set<UserRating> userRatedMovies, Set<Integer> similarUsers) {
        return ratingManager.getRatings().stream()
                .filter(ur -> similarUsers.contains(ur.getUserId()))
                .filter(ur -> nonRatedMovie(userRatedMovies, ur))
                .filter(ur -> ur.getRating() > 2)
                .map(UserRating::getMovieId)
                .distinct()
                .collect(toList());
    }

    boolean nonRatedMovie(Set<UserRating> userRatings, UserRating target) {
        return userRatings.stream()
                .noneMatch(ur -> ur.getMovieId() == target.getMovieId());
    }

    Set<Integer> usersWithSimilarInterests(int userId, Set<UserRating> userRatedMovies) {
        return ratingManager.getRatings().stream()
                .filter(ur -> ur.getUserId() != userId)
                .filter(ur -> matchingMovieRating(userRatedMovies, ur))
                .map(UserRating::getUserId)
                .collect(toSet());
    }

    private boolean matchingMovieRating(Set<UserRating> userRatings, UserRating target) {
        return userRatings.stream()
                .anyMatch(ur -> ur.getMovieId() == target.getMovieId()
                        && ur.getRating() == target.getRating());
    }

}
