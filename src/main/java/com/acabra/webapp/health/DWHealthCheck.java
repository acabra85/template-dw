package com.acabra.webapp.health;

import com.codahale.metrics.health.HealthCheck;

public class DWHealthCheck extends HealthCheck {
    public DWHealthCheck(String template) {

    }

    @Override
    protected Result check() throws Exception {
        return null;
    }
}
