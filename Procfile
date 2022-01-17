web:  java $JAVA_OPTS -Ddw.server.connector.port=$PORT -jar target/webapp-1.0-SNAPSHOT.jar server config/heroku.yml
scheduler:  java $JAVA_OPTS -cp target/classes:target/dependency/* com.acabra.webapp.job.CleanerJob
