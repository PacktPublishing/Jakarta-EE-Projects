package org.jakartaeeprojects.recommendation.suggestion.control;

import java.util.List;

public interface SuggestionGenerator {

    List<Integer> suggestMoviesForUser(int userId);

}
