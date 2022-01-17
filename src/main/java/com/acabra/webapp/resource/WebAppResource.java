package com.acabra.webapp.resource;

import com.acabra.webapp.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicLong;


@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class WebAppResource implements AppResource {

    private final AtomicLong reqCount;

    public WebAppResource() {
        reqCount = new AtomicLong();
    }

    @GET
    public Response greet() {
        return getResponse(Response.Status.OK, "test message", okResponse());
    }

    private SimpleResponse okResponse() {
        return new SimpleResponse(reqCount.getAndIncrement(), false) {
            @Override
            public long getId() {
                return id;
            }

            @Override
            public boolean isFailure() {
                return failure;
            }
        };
    }
}
