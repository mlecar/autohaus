package com.mlc.autohaus.repository;

import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJdbcRepositories(considerNestedRepositories = true)
public class RepositoryConfiguration {
}
