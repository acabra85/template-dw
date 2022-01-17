package com.acabra.webapp.control;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WebAppManager implements Cleaner {

    private final String greetTemplate;
    private final String defaultName;

    public WebAppManager(String greetTemplate, @NotEmpty String defaultName) {
        this.greetTemplate = greetTemplate;
        this.defaultName = defaultName;
    }

    @Override
    public CompletableFuture<Integer> clean(LocalDateTime lastRun, TimeUnit unit, long duration) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("maintenance task run");
            return 1;
        });
    }

    public String buildId() {
        return UUID.randomUUID().toString();
    }

    public String greet(String name) {
        if(null != name && !name.isBlank()) {
            return String.format(greetTemplate, name);
        }
        return String.format(greetTemplate, defaultName);
    }
}
