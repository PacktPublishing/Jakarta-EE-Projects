package org.jakartaeeprojects.moviecloud.movie.control;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jakartaeeprojects.moviecloud.movie.entity.Movie;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class DemoClient {

    @Inject
    Logger logger;

    @Fallback(fallbackMethod = "defaultResponse")
    @Timeout(500)
    public String longRunningOperation() {
        logger.log(Level.INFO, "invoked longRunningOperation");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            //
        }
        return "This would never return!";
    }

    public String defaultResponse() {
        return "Fallback response after timeout";
    }


    @Timeout(500)
    @Bulkhead(2)
    @Fallback(RecommendationFallbackHandler.class)
    @CircuitBreaker(requestVolumeThreshold = 3)
    public List<Movie> externalServiceCall() {
        logger.log(Level.INFO, "==============  invoked externalServiceCall =======");
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            //
        }
        logger.log(Level.INFO, "=================  return null =================");
        return null;
    }
}
