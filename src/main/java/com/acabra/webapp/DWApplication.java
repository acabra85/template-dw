package com.acabra.webapp;

import com.acabra.webapp.control.WebAppManager;
import com.acabra.webapp.health.DWHealthCheck;
import com.acabra.webapp.job.JobCleanPolicyPOJO;
import com.acabra.webapp.job.JobManager;
import com.acabra.webapp.resource.WebAppResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;

public class DWApplication extends Application<DWApplicationConfig> {

    /**
     * Provides configuration for Cross Origin Requests
     *
     * @param environment the DropWizard's environment
     */
    private void configureCORS(Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets()
                .addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    /**
     * Allows static files to be served as resources
     *
     * @param bootstrap
     */
    private void provideResolutionForStaticAssets(Bootstrap<DWApplicationConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html", "html"));
    }

    public static void main(String[] args) throws Exception {
        new DWApplication().run(args);
    }

    @Override
    public String getName() {
        return DWApplicationConfig.applicationName;
    }

    @Override
    public void initialize(Bootstrap<DWApplicationConfig> bootstrap) {
        provideResolutionForStaticAssets(bootstrap);
    }

    @Override
    public void run(DWApplicationConfig configuration, Environment environment) {
        configureCORS(environment);
        registerResourceControllers(configuration, environment);
        registerHealthChecks(configuration, environment);
        startJobManager();
    }

    private void registerResourceControllers(DWApplicationConfig config, Environment environment) {
        environment.jersey().setUrlPattern(config.getContextPath());
        environment.jersey().register(new WebAppResource());
    }

    private void registerHealthChecks(DWApplicationConfig configuration, Environment environment) {
        environment.healthChecks().register("template", new DWHealthCheck(configuration.getTemplate()));
    }

    private void startJobManager() {
        new JobManager(new WebAppManager(), new JobCleanPolicyPOJO(ChronoUnit.MINUTES, 15L))
                .start();
    }
}
