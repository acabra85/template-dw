# template-dw
A [DropWizard](https://www.dropwizard.io/en/latest/getting-started.html) ready running http server, pre-configured with:

* [JUnit5](https://junit.org/junit5/)
* [AssertJ](https://assertj.github.io/doc/)
* [SureFire](https://maven.apache.org/surefire/maven-surefire-plugin/)
* [Jacoco](https://www.eclemma.org/jacoco/trunk/doc/maven.html)
* [SpotBugs](https://spotbugs.readthedocs.io/en/latest/maven.html)

It is ready for the cloud ([Heroku](https://devcenter.heroku.com/articles/deploying-java-applications-with-the-heroku-maven-plugin))

# Health Check
http://localhost:8080/admin/healthcheck

# Metrics
http://localhost:8080/admin/metrics

# Run
```mvn clean install && java -jar target/webapp-1.0-SNAPSHOT.jar config/webapp.yml```