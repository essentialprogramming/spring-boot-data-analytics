package com.util.async;



import com.util.environment.Environment;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorsProvider class provides static access to application shared ExecutorServices to be used by asynchronous
 * methods (tasks implemented using CompletableFutures that run asynchronously).
 */
public class ExecutorsProvider {

    private static final int SUBMITTED_TASKS_QUEUE_SIZE = Environment.getProperty("tasks.queue.size", 1000);

    private static class ExecutorsServiceHolder {
        static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        static final ManagedThreadPoolExecutor managedExecutorService = getManagedAsyncExecutor();
    }


    public static ExecutorService getExecutorService() {
        return ExecutorsServiceHolder.executorService;
    }

    public static ManagedThreadPoolExecutor getManagedExecutorService() {
        return ExecutorsServiceHolder.managedExecutorService;
    }


    private static ManagedThreadPoolExecutor getManagedAsyncExecutor() {
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        int maxPoolSize = corePoolSize * 2;

        final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        final BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(SUBMITTED_TASKS_QUEUE_SIZE);

        final ManagedThreadPoolExecutor executor = new ManagedThreadPoolExecutor(
                corePoolSize, maxPoolSize,
                0L, TimeUnit.MILLISECONDS,
                "async",
                queue,
                handler
        );

        // Let's start all core threads initially
        executor.prestartAllCoreThreads();
        return executor;
    }
}


