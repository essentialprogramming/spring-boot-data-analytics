package com.base.config;

import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class JooqProfileBasedConfig {

    public static final String JOOQ_CODEGEN_SCHEMA = "jooq_codegen";

    @Bean
    @Profile("h2")
    public Settings h2Settings() {
        return new Settings()
                .withRenderMapping(
                        new RenderMapping()
                                .withSchemata(new MappedSchema()
                                        .withInput(JOOQ_CODEGEN_SCHEMA))
                                )
                .withRenderNameCase(RenderNameCase.AS_IS);
    }

    @Bean
    @Profile("postgresql")
    public Settings postgresqlSettings() {
        return new Settings()
                .withRenderMapping(
                        new RenderMapping()
                                .withSchemata(new MappedSchema()
                                        .withInput(JOOQ_CODEGEN_SCHEMA))
                )
                .withRenderNameCase(RenderNameCase.LOWER);
    }
}
