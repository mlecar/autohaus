package com.mlc.autohaus;

import com.mlc.autohaus.model.RunningPostgresDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootTest
class AutohausApplicationTests {

    @TestConfiguration
    static class RepositoryTestConfiguration {
        @Bean
        public DataSource dataSource() {
            return RunningPostgresDatabase.getDataSource();
        }
    }

    @Test
    void contextLoads() {
    }

}
