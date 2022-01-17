package com.acabra.webapp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class DWApplicationConfig extends Configuration {
    @NotEmpty final private String greetTemplate;
    @NotEmpty final private String defaultName;
    @NotEmpty final private String appContextPath;

    @JsonCreator
    public DWApplicationConfig(@JsonProperty("greetTemplate") String greetTemplate,
                               @JsonProperty("defaultName") String defaultName,
                               @JsonProperty("appContextPath") String appContextPath) {
        this.greetTemplate = greetTemplate;
        this.defaultName = defaultName;
        this.appContextPath = appContextPath;
    }
}
