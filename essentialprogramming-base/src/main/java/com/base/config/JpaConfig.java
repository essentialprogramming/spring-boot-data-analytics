package com.base.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories (basePackages = "com.base.persistence.repository")
@EntityScan (basePackages = {"com.base.persistence.entities", "com.base.persistence.model"})
public class JpaConfig {
}
