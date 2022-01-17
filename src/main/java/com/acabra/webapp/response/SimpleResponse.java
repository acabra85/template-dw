package com.acabra.webapp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class SimpleResponse implements Serializable {

    protected final long id;
    protected final boolean failure;

    @JsonCreator
    protected SimpleResponse(@JsonProperty("id") long id, @JsonProperty("failure") boolean failure){
        this.id = id;
        this.failure = failure;
    }

}