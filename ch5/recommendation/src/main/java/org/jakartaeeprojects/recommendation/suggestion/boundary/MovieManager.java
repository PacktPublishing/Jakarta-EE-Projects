package org.jakartaeeprojects.recommendation.suggestion.boundary;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.context.ApplicationScoped;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.jakartaeeprojects.recommendation.suggestion.entity.UserRating;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@ApplicationScoped
public class MovieManager {
    private static Map<Long, Set<UserRating>> ratingsMap;

    @PostConstruct
    public void init() {
        Path path = getRatingCSVPath();
        try (Stream<String> stream = Files.lines(path)) {
            Map<Long, Set<UserRating>> data = stream
                    .map(this::getUserRating)
                    .collect(groupingBy(UserRating::getUserId, toSet()));

            ratingsMap = data;
        } catch (Exception ioe) {
            throw new IllegalStateException("Failed to init ratings data", ioe);
        }
    }

    private Path getRatingCSVPath() {
        URI uri = null;
        try {
            uri = getClass().getClassLoader().getResource("ratings.csv").toURI();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to load CSV");
        }
        return Paths.get(uri);
    }

    private UserRating getUserRating(String line) {
        String[] movie = line.split(",");
        return new UserRating(Long.valueOf(movie[0]),
                Long.valueOf(movie[1]),
                Integer.valueOf(movie[2]));
    }

    public Map<Long, Set<UserRating>> getRatingsMap() {
        return ratingsMap;
    }

}
