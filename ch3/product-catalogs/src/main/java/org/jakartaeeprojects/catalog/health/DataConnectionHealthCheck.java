package org.jakartaeeprojects.catalog.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Health
@ApplicationScoped
public class DataConnectionHealthCheck implements HealthCheck {
    static final int TIME_OUT = 2;

    @Inject
    private Logger logger;

    @Resource
    private DataSource datasource;

    @Override
    public HealthCheckResponse call() {
        boolean isWorking = false;
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("dataConnection");

        try (Connection connection = datasource.getConnection()) {
            isWorking = connection.isValid(TIME_OUT);

            return responseBuilder
                    .up()
                    .withData("connection", isWorking)
                    .build();
        } catch(SQLException e) {
            logger.log(Level.SEVERE, "Checking connection failed", e);
        }

        return responseBuilder
                .down()
                .withData("connection", isWorking)
                .build();
    }

}
