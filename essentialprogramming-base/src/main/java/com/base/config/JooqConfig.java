package com.base.config;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import com.base.exception.ExceptionTranslator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.StringUtils;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class JooqConfig {

    private static final String H2_NAME = "h2";
    private static final String JOOQ_CODEGEN_SCHEMA = "jooq_codegen";

    private final DataSource dataSource;

    /**
     * Get a DSLContext.
     *
     * @return A DSLContext connected to the provided database.
     */
    @Bean
    public DefaultDSLContext defaultDSLContext() {
        return new DefaultDSLContext(jooqConfiguration());
    }

    public DefaultConfiguration jooqConfiguration() {
        final DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        final String databaseVendorName = this.getDatabaseVendor();

        RenderNameCase renderNameCase = RenderNameCase.LOWER;
        if (StringUtils.hasLength(databaseVendorName) && databaseVendorName.equals(H2_NAME)) {
            renderNameCase = RenderNameCase.AS_IS;
        }

        jooqConfiguration.setSettings(settings().withRenderNameCase(renderNameCase));
        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(new ExceptionTranslator()));

        return jooqConfiguration;
    }

    public Settings settings() {
        return new Settings()
                .withRenderMapping(
                        new RenderMapping()
                                .withSchemata(new MappedSchema()
                                        .withInput(JOOQ_CODEGEN_SCHEMA))
                )
                .withRenderNameCase(RenderNameCase.AS_IS);
    }

    private String getDatabaseVendor() {
        final Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            String dbProductName = connection.getMetaData().getDatabaseProductName();
            return dbProductName.toLowerCase();
        } catch (SQLException e) {
            log.info("Error occurred while trying to retrieve the database vendor " + e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        return null;
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        final TransactionAwareDataSourceProxy awareDataSourceProxy
                = new TransactionAwareDataSourceProxy(dataSource);

        return new DataSourceConnectionProvider(awareDataSourceProxy);
    }

}
