package com.acabra.webapp.job;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public class JobCleanPolicyPOJO {
    private final TimeUnit unit;
    private final long duration;
}
