package com.base.config;

import javax.sql.DataSource;

import com.base.exception.ExceptionTranslator;
import lombok.RequiredArgsConstructor;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
@RequiredArgsConstructor
public class JooqConfig {

    private final DataSource dataSource;
    private final Settings jooqSettings;

    /**
     * Get a DSLContext.
     *
     * @return A DSLContext connected to the provided database.
     */
    @Bean
    public DefaultDSLContext defaultDSLContext() {
        return new DefaultDSLContext(jooqConfiguration());
    }

    public org.jooq.Configuration jooqConfiguration() {
        final DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.setSettings(jooqSettings);
        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(new ExceptionTranslator()));

        return jooqConfiguration;
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        final TransactionAwareDataSourceProxy awareDataSourceProxy
                = new TransactionAwareDataSourceProxy(dataSource);

        return new DataSourceConnectionProvider(awareDataSourceProxy);
    }

}
