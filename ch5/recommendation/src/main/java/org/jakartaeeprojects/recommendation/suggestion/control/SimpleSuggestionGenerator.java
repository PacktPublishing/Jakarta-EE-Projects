package org.jakartaeeprojects.recommendation.suggestion.control;

import org.jakartaeeprojects.recommendation.suggestion.boundary.RatingManager;
import org.jakartaeeprojects.recommendation.suggestion.entity.UserRating;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class SimpleSuggestionGenerator implements SuggestionGenerator {

    @Inject
    private Logger logger;

    @Inject
    private RatingManager ratingManager;

    @Override
    public List<Integer> suggestMoviesForUser(int userId) {
        logger.log(Level.INFO, "ratings " + ratingManager.getRatingsMap());
        Set<Integer> userRatedMovies = ratingManager.getRatingsMap()
                .get(userId).stream()
                .map(UserRating::getMovieId)
                .collect(toSet());
        System.out.println("findTopRatedMovies ");
        return findTopRatedMovies(userRatedMovies);
    }

    private List<Integer> findTopRatedMovies(Set<Integer> ratedMoviesByUser) {
        return getAllUserRatingStream()
                .filter(ur -> !ratedMoviesByUser.contains(ur.getMovieId()))
                .sorted(comparing(UserRating::getRating).reversed())
                .map(UserRating::getMovieId)
                .distinct()
                .collect(toList());
    }

    private Stream<UserRating> getAllUserRatingStream() {
        return ratingManager.getRatingsMap().entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(Set::stream);
    }

}
