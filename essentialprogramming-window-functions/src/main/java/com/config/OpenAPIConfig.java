package com.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    /*
    Data Analytics API
    */
    @Bean
    public GroupedOpenApi dataAnalyticsApi() {
        final String[] packagesToScan = {AppConfig.API_PACKAGE};
        return GroupedOpenApi
                .builder()
                .group("Data Analytics API")
                .packagesToScan(packagesToScan)
                .pathsToMatch("/**")
                .addOpenApiCustomiser(dataAnalyticsApiCustomizer())
                .build();
    }

    private OpenApiCustomiser dataAnalyticsApiCustomizer() {
        return openAPI -> openAPI
                .info(new Info()
                        .title(AppConfig.APP_TITLE)
                        .description("Data Analytics Services using OpenAPI")
                        .version("1.0.0"));
    }


}
