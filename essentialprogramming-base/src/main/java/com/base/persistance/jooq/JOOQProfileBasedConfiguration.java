package com.base.persistance.jooq;

import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class JOOQProfileBasedConfiguration {

    @Bean
    @Profile("h2")
    public Settings h2Settings() {
        return new Settings().withRenderNameCase(RenderNameCase.AS_IS);
    }

    @Bean
    @Profile("postgresql")
    public Settings postgresqlSettings() {
        return new Settings().withRenderNameCase(RenderNameCase.LOWER);
    }
}
