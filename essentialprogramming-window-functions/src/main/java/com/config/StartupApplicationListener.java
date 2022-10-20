package com.config;

import com.util.async.ExecutorsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    public StartupApplicationListener() {
    }

    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        log.info("Application started..");

        Runtime.getRuntime().addShutdownHook(new Thread( ()
                -> ExecutorsProvider.getManagedExecutorService().stop(), "appShutdownHook")
        );

    }
}
