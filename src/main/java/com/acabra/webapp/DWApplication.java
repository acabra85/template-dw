package com.acabra.webapp;

import com.acabra.webapp.control.Cleaner;
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
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class DWApplication extends Application<DWApplicationConfig> {

    private static final String APPLICATION_NAME = "webapp";

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
        return APPLICATION_NAME;
    }

    @Override
    public void initialize(Bootstrap<DWApplicationConfig> bootstrap) {
        provideResolutionForStaticAssets(bootstrap);
    }

    @Override
    public void run(DWApplicationConfig configuration, Environment environment) {
        configureCORS(environment);
        WebAppManager manager = new WebAppManager(configuration.getGreetTemplate(), configuration.getDefaultName());
        registerResourceControllers(configuration, environment, manager);
        registerHealthChecks(configuration, environment);
        startJobManager(manager);
    }

    private void registerResourceControllers(DWApplicationConfig config, Environment environment, WebAppManager manager) {
        environment.jersey().setUrlPattern(config.getAppContextPath());
        environment.jersey().register(new WebAppResource(manager));
    }

    private void registerHealthChecks(DWApplicationConfig configuration, Environment environment) {
        //this is a sample health check to validate the template
        environment.healthChecks().register("greetTemplate", new DWHealthCheck(configuration.getGreetTemplate()));
    }

    private void startJobManager(Cleaner manager) {
        new JobManager(manager, new JobCleanPolicyPOJO(TimeUnit.MINUTES, 15L))
                .start();
    }
}
