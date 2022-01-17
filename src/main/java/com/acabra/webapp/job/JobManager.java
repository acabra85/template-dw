package com.acabra.webapp.job;

import com.acabra.webapp.control.Cleaner;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
public class JobManager {

    private final SimpleScheduleBuilder EVERY_15_MINUTES = simpleSchedule()
            .withIntervalInMinutes(15)
            .repeatForever();

    private final Scheduler scheduler;
    private final Cleaner task;
    private final JobCleanPolicyPOJO policy;
    private volatile boolean started;

    public JobManager(Cleaner task, JobCleanPolicyPOJO cleanPolicy) {
        Scheduler tmpScheduler;
        this.task = task;
        this.policy = cleanPolicy;
        try {
            tmpScheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            tmpScheduler = null;
        }
        this.scheduler = tmpScheduler;
    }

    public void start() {
        try {
            logger.info("trying to start");
            if (this.scheduler == null ) {
                // Grab the Scheduler instance from the Factory
                logger.error("Wrong instantiation of job... no scheduler present");
                return;
            }
            if (task!= null &&  !this.scheduler.isStarted() && !this.started) {
                // and start it off
                scheduler.start();
                logger.info("web calculation history scheduler started");

                scheduler.getContext().put("task", this.task);
                scheduler.getContext().put("policy", this.policy);

                scheduleWebCalculatorHistoryCleanerJob();
                this.started = !(scheduler.isShutdown() && scheduler.isInStandbyMode());
            }
        } catch (SchedulerException se) {
            logger.info("failed_ " + se.getMessage());
            logger.error("error", se);
        }
    }

    private void scheduleWebCalculatorHistoryCleanerJob() throws SchedulerException {
        JobDetail syncJobDetail = newJob(CleanerJob.class)
                .withIdentity("historyCleanJob", "job_group_1")
                .build();

        Trigger syncTrigger = newTrigger()
                .withIdentity("syncTrigger", "job_group_1")
                .startNow()
                .withSchedule(EVERY_15_MINUTES)
                .build();

        scheduler.scheduleJob(syncJobDetail, syncTrigger);
    }


    public void shutDown() {
        try {
            if (!this.started) {
                throw new IllegalStateException("The system has not been yet turned on");
            }
            if (scheduler!= null && scheduler.isStarted()) {
                scheduler.shutdown();
                this.started = !scheduler.isShutdown();
            }
        } catch (SchedulerException se) {
            logger.error("scheduler exception", se);
        }
    }

    public boolean isStarted() {
        return started;
    }
}
