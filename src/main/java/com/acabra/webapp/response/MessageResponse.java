package com.acabra.webapp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties
@JsonInclude
@Getter
public class MessageResponse<T> extends SimpleResponse {

    private final String message;
    private final T body;

    @JsonCreator
    public MessageResponse(@JsonProperty("id") long id,
                           @JsonProperty("failure") boolean failure,
                           @JsonProperty("message") String message,
                           @JsonProperty("body") T body) {
        super(id, failure);
        this.message = message;
        this.body = body;
    }
}
