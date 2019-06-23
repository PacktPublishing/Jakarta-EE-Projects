package org.jakartaeeprojects.moviecloud.movie.control;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/suggestions")
@RegisterRestClient(baseUri = "http://recommendation:8080/recommendation/resources")
@RegisterClientHeaders
public interface SuggestionResourceService {

	@GET
	@Path("/{userId}")
	public List<Long> findSuggested(@PathParam("userId") long userId);
}
