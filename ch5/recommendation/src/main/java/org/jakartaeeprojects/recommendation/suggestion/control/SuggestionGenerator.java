package org.jakartaeeprojects.recommendation.suggestion.control;

import java.util.List;

public interface SuggestionGenerator {

    List<Long> suggestMoviesForUser(long userId);

}
