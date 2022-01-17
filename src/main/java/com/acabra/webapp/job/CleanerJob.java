package com.acabra.webapp.job;

import com.acabra.webapp.control.Cleaner;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.time.LocalDateTime;

@Slf4j
public class CleanerJob implements Job {
    public static final String CLEANER_CTX_KEY = "cleaner";
    public static final String POLICY_CTX_KEY = "policy";

    private LocalDateTime lastRun = null;
    private JobCleanPolicyPOJO policy;
    private Cleaner cleaner;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Hello World! - Running job");

        if (lastRun == null) {
            lastRun = LocalDateTime.now();
        }
        try {
            logger.info("last run " + lastRun);
            provideJobInputs(jobExecutionContext.getScheduler().getContext());
            cleaner.clean(this.lastRun, this.policy.getUnit(), this.policy.getDuration())
                    .thenAccept(cleanedEntries -> lastRun = LocalDateTime.now());
        } catch (SchedulerException e) {
            logger.error("error", e);
        }
    }

    private void provideJobInputs(SchedulerContext schedulerContext) {
        if (null == this.cleaner) {
            this.cleaner = (Cleaner) schedulerContext.get(CLEANER_CTX_KEY);
        }
        if (null == this.policy) {
            this.policy = (JobCleanPolicyPOJO) schedulerContext.get(POLICY_CTX_KEY);
        }
        if (this.policy == null || this.cleaner == null){
            throw new NullPointerException("unable to retrieve cleaner job inputs");
        }
    }

    public LocalDateTime getLastRun() {
        return lastRun;
    }
}
