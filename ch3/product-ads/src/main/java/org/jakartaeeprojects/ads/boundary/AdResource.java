package org.jakartaeeprojects.ads.boundary;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.jakartaeeprojects.ads.entity.ProductAd;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("ads")
@Produces(MediaType.APPLICATION_JSON)
public class AdResource {

    @Inject
    private Logger logger;

    @Inject
    private AdProvider adProvider;

    @Counted(name = "getAds",
            absolute = true,
            monotonic = true,
            description = "Number of times ads served")
    @GET
    @Path("/{category}")
    public Response getAds(@NotBlank @PathParam("category") String category,
                           @Context HttpHeaders headers) {
        printHeaders(headers);

        logger.log(Level.INFO,"Fetching ads for " + category);


        List<ProductAd> productAds = this.adProvider.lookup(category);
        if(productAds.isEmpty()) {
            return fallback();
        }
        return Response.ok(productAds)
                .build();
    }

    private void printHeaders(HttpHeaders headers) {
        MultivaluedMap<String, String> rh = headers.getRequestHeaders();
        String str = rh.entrySet()
                .stream()
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining("\n"));

        logger.log(Level.INFO, str);
    }

    public Response fallback() {
        logger.log(Level.INFO,"fallback ads");
        return Response.ok(this.adProvider.defaultAds())
                .build();
    }

}