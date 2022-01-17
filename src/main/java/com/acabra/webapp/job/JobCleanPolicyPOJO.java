package com.acabra.webapp.job;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Getter
public class JobCleanPolicyPOJO {
    private final ChronoUnit unit;
    private final long duration;
}
