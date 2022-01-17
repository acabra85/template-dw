package com.acabra.webapp.health;

import com.codahale.metrics.health.HealthCheck;

public class DWHealthCheck extends HealthCheck {
    private final String greetTemplate;

    public DWHealthCheck(String greetTemplate) {
        this.greetTemplate = greetTemplate;
    }

    @Override
    protected Result check() throws Exception {
        if("Hello, TEST!".equals(String.format(greetTemplate, "TEST"))) {
            return Result.healthy();
        }
        return Result.unhealthy("Wrong template loaded: Please verify yml configuration");
    }
}
