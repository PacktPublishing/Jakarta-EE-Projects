package org.jakartaeeprojects.catalog.boundary;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.jakartaeeprojects.catalog.control.AuthorService;
import org.jakartaeeprojects.catalog.entity.Author;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @Inject
    private AuthorService service;


    @Metered(name = "authorsRetrieved",
            unit = MetricUnits.MINUTES,
            description = "Monitor authors",
            absolute = true)
    @GET
    public Response getAuthors(@DefaultValue("0") @QueryParam("start") int start,
                               @DefaultValue("5") @QueryParam("limit") int limit) {
        List<Author> authorList = this.service.getAuthors(start, limit);

        return Response.ok(authorList).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthor(@PathParam("id") long authorId) {
        Optional<Author> author = this.service.getAuthor(authorId);
        if (author.isPresent()) {
            return Response.ok(author).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .header("reason", "No author found by " + authorId)
                .build();
    }
}
