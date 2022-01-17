package com.acabra.webapp.resource;

import com.acabra.webapp.response.SimpleResponse;

import javax.ws.rs.core.Response;

public interface AppResource {

    /**
     * Builds a Http response based on a status code (HTTP) and a message
     *
     * @param status  An HTTP status response code
     * @param body    the body containing the answer
     * @return a well formed http response
     */
    default Response getResponse(Response.Status status, SimpleResponse body) {
        return Response.status(status)
                .entity(body)
                .build();
    }
}