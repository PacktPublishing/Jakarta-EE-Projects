package org.jakartaeeprojects.moviecloud.movie.control;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/ratings")
@RegisterRestClient
@RegisterClientHeaders
public interface UserRatingResourceService {

	@PUT
	public Response rateMovie(@QueryParam("userId") long userId, 
			@QueryParam("movieId") long movieId,
			@QueryParam("rating") long rating);
}
