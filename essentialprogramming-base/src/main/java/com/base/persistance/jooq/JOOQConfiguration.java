package com.base.persistance.jooq;

import javax.sql.DataSource;

import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class JOOQConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Settings jooqSettings;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        TransactionAwareDataSourceProxy awareDataSourceProxy = new
            TransactionAwareDataSourceProxy(dataSource);
        return new DataSourceConnectionProvider(awareDataSourceProxy);
    }

    @Bean
    public DefaultDSLContext defaultDSLContext() {
        return new DefaultDSLContext(configuration());
    }

    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.setSettings(jooqSettings);
        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(new ExceptionTranslator()));

        return jooqConfiguration;
    }
}
