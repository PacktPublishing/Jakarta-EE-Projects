package org.jakartaeeprojects.catalog.config;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MissingEntityException
        implements ExceptionMapper<IllegalArgumentException> {

    public Response toResponse(IllegalArgumentException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .header("reason", e.getMessage())
                .build();
    }

}
