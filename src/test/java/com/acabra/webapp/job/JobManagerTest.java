package com.acabra.webapp.job;

import com.acabra.webapp.control.Cleaner;
import com.acabra.webapp.control.WebAppManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class JobManagerTest {

    private final Cleaner cleanerStub = new WebAppManager("template, %s", "Stranger");
    private final JobCleanPolicyPOJO policyStub = new JobCleanPolicyPOJO(TimeUnit.SECONDS, 30);
    private JobManager underTest;

    @BeforeEach
    public void setup() {
        underTest = new JobManager(cleanerStub, policyStub);
    }

    @Test
    public void startStopTest() {
        Assertions.assertThat(underTest.isStarted()).isFalse();
        underTest.start();
        Assertions.assertThat(underTest.isStarted()).isTrue();
        underTest.shutDown();
        Assertions.assertThat(underTest.isStarted()).isFalse();
    }

    @Test
    public void failureStopTest() {
        Assertions.assertThat(underTest.isStarted()).isFalse();
        Assertions.assertThatThrownBy(() -> underTest.shutDown())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The system has not been yet turned on");
    }

}