package com;

import com.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;


/**
 * Entry point for SpringBootApplication
 */
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = AppConfig.ROOT_PACKAGE)

public class DataAnalyticsApplication {

    private final BuildProperties buildProperties;

    public static void main(String[] args) {
        SpringApplication.run(DataAnalyticsApplication.class, args);
    }


}
