package com.acabra.webapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotEmpty;

public class DWApplicationConfig extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    protected final static String defaultName = "webappdefaultname";

    @NotEmpty
    protected final static String applicationName = "webapp";

    @NotEmpty
    private String contextPath;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getApplicationName() {
        return applicationName;
    }

    @JsonProperty
    public String getContextPath() {
        return contextPath;
    }
}
