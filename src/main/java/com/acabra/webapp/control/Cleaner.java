package com.acabra.webapp.control;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface Cleaner {
    CompletableFuture<Integer> clean(LocalDateTime lastRun, TimeUnit unit, long duration);
}
