package org.jakartaeeprojects.moviecloud.movie.control;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jakartaeeprojects.moviecloud.movie.boundary.MovieCatalog;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

@ApplicationScoped
public class RecommendationClient {

    public static final String URI = "http://recommendation:8080/recommendation/resources/suggestions/{id}";
    @Inject
    private Logger logger;

    @Inject
    private MovieCatalog catalog;

	private Client client;
    private WebTarget target;

    @PostConstruct
    private void initClient() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(800, TimeUnit.MILLISECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();
        target = client.target(URI);
    }

    @Fallback(fallbackMethod = "getDefaultRecommendations")
    @Timeout(300)
    public List<Long> getRecommendations(long id) {
    	Response response = sendRequest(id);
    	return response.readEntity(new GenericType<List<Long>>() {});              
    }

    public List<Long> getDefaultRecommendations(long id) {
        logger.log(Level.INFO, "using the fallback");
        return catalog.defaultRecommendation();
    }

    private Response sendRequest(long id) {        
        return target.resolveTemplate("id", id)
                .request()
                .get();        
    }

    @PreDestroy
    private void closeClient() {
        client.close();
    }

}