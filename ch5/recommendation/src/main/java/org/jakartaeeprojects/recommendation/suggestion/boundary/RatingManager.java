package org.jakartaeeprojects.recommendation.suggestion.boundary;

import org.jakartaeeprojects.recommendation.suggestion.entity.UserRating;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.jakartaeeprojects.recommendation.suggestion.entity.UserRating.rate;

@ApplicationScoped
public class RatingManager {
    private Set<UserRating> ratings = new HashSet<>();

    @PostConstruct
    public void init() {
        Collections.addAll(ratings,
                rate(10, 1, 4),
                rate(10, 2, 2),
                rate(10, 3, 5),
                rate(10, 7, 2),

                rate(11, 1, 4),
                rate(11, 2, 2),
                rate(11, 4, 3),
                rate(11, 6, 3),

                rate(12, 1, 2)
        );
    }

    public Set<UserRating> getRatings() {
        return ratings;
    }

    public void addOrUpdateUserRating(UserRating rating) {
        boolean added = this.ratings.add(rating);
        if (!added) {
            this.ratings.remove(rating);
            this.ratings.add(rating);
        }
    }
}
