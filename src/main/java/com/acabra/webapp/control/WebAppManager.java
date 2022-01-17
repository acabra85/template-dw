package com.acabra.webapp.control;

import java.util.UUID;

public class WebAppManager implements Cleaner {

    @Override
    public void clean() {
        System.out.println("maintenance task run");
    }

    public String buildId() {
        return UUID.randomUUID().toString();
    }
}
