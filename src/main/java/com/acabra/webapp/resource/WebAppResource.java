package com.acabra.webapp.resource;

import com.acabra.webapp.control.WebAppManager;
import com.acabra.webapp.response.MessageResponse;
import com.acabra.webapp.response.SimpleResponse;
import com.codahale.metrics.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicLong;


@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class WebAppResource implements AppResource {

    private final AtomicLong reqCount;
    private final WebAppManager manager;

    public WebAppResource(WebAppManager manager) {
        this.manager = manager;
        this.reqCount = new AtomicLong();
    }

    @GET
    @Timed
    public Response greet(@QueryParam("name") String name) {
        SimpleResponse response = new MessageResponse<>(reqCount.getAndIncrement(), false,
                manager.buildId(), manager.greet(name));
        return getResponse(Response.Status.OK, response);
    }
}
