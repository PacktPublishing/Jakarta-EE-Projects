package org.jakartaeeprojects.recommendation.suggestion.control;

import org.jakartaeeprojects.recommendation.suggestion.boundary.MovieManager;
import org.jakartaeeprojects.recommendation.suggestion.entity.UserRating;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class SimpleSuggestionGenerator implements SuggestionGenerator {

    private MovieManager movieManager;

    @Inject
    public SimpleSuggestionGenerator(MovieManager movieManager) {
        this.movieManager = movieManager;
    }

    @Override
    public List<Long> suggestMoviesForUser(long userId) {
        List<Long> userRatedMovies = movieManager.getRatingsMap()
                .get(userId).stream()
                .map(UserRating::getMovieId)
                .collect(toList());
        System.out.println("User rated movies " + userRatedMovies);
        List<Long> highRatedMovies = getHighRatedMovies(userRatedMovies);
        System.out.println("High rated movies " + highRatedMovies);
        return highRatedMovies;
    }


    private List<Long> getHighRatedMovies(List<Long> userRatedMovies) {
        return getUserRatingStream()
                .filter(ur -> !userRatedMovies.contains(ur.getMovieId()))
                .sorted(comparing(UserRating::getRating).reversed())
                .map(UserRating::getMovieId)
                .distinct()
                .collect(toList());
    }

    private Stream<UserRating> getUserRatingStream() {
        return movieManager.getRatingsMap().entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(Set::stream);
    }

}
