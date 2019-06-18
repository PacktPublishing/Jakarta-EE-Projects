package org.jakartaeeprojects.recommendation.suggestion;

import org.jakartaeeprojects.recommendation.suggestion.boundary.MovieManager;
import org.jakartaeeprojects.recommendation.suggestion.control.SimpleSuggestionGenerator;
import org.jakartaeeprojects.recommendation.suggestion.entity.UserRating;

import java.util.Map;
import java.util.Set;

public class Test {
    private static Map<Long, Set<UserRating>> ratingsMap;

    public static void main(String[] args) {
        new Test().doIt();
    }

    private void doIt() {
        MovieManager movieManager = new MovieManager();
        movieManager.init();
        new SimpleSuggestionGenerator(movieManager).suggestMoviesForUser(12);

    }
}
