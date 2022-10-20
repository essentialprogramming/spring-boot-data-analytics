package com.util.async;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

public class ManagedThreadPoolExecutor extends ThreadPoolExecutor {

    private static final Logger log = LoggerFactory.getLogger(ManagedThreadPoolExecutor.class);

    public ManagedThreadPoolExecutor(
            final int corePoolSize,
            final int maximumPoolSize,
            final long keepAliveTime,
            final TimeUnit unit,
            final String poolName,
            final BlockingQueue<Runnable> workQueue,
            final RejectedExecutionHandler handler) {

        super(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                new NamedThreadFactory(poolName),
                handler
        );
    }

    public void stop() {
        shutdown();
        log.info("ManagedExecutorService - stopping (waiting for all tasks to complete - max 300 seconds)");
        try {
            if (!awaitTermination(300, TimeUnit.SECONDS)) {
                shutdownNow();
            }
            log.info("ManagedExecutorService stopped");
        } catch (InterruptedException e) {
            shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    private static class NamedThreadFactory implements ThreadFactory {

        private final String poolName;
        private final ThreadFactory threadFactory;

        public NamedThreadFactory(final String poolName) {
            this.poolName = poolName;
            threadFactory = Executors.defaultThreadFactory();
        }

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            final Thread thread = threadFactory.newThread(runnable);
            thread.setName(thread.getName()
                    .replace("pool", poolName)
                    .replace("-thread-", "-exec-")
            );

            return thread;
        }
    }
}
