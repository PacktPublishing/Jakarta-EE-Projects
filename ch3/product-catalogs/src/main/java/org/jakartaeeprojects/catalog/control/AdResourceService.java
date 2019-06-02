package org.jakartaeeprojects.catalog.control;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ads")
@RegisterRestClient
@RegisterClientHeaders
public interface AdResourceService {

    @GET
    @Path("/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Ad> getAds(@PathParam("category") String cat);
}


