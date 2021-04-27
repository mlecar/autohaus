package com.mlc.autohaus.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

public class RunningPostgresDatabase {
    private static final Logger logger = LoggerFactory.getLogger(RunningPostgresDatabase.class);
    
    private static PostgreSQLContainer postgres;

    static {
        try {
            startPostgres();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        addShutdownHook();
    }

    public static DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .url(postgres.getJdbcUrl())
                .username(postgres.getUsername())
                .password(postgres.getPassword())
                .build();
    }

    private static void startPostgres() throws Exception {
        postgres = new PostgreSQLContainer(PostgreSQLContainer.IMAGE + ":11");
        postgres.start();
    }

    private static void addShutdownHook() {
        logger.info("Adding hook to shut-down postgres server");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.info("Shutting-down embedded postgres Server");
                postgres.stop();
            }
        });
    }
}
