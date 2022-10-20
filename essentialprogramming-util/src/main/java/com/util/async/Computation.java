package com.util.async;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Computation {

	private static final Logger LOG = LoggerFactory.getLogger(Computation.class);

	private Computation() {
	}

	/**
	 * Wrapper method to return a CompletableFuture that calls the given callable asynchronously. Wraps and handles the
	 * callable's exceptions by explicitly completing the CompletableFuture exceptionally.
	 * @param callable Code to be executed
	 * @param executorService The ExecutorService
	 */
	public static <R> CompletableFuture<R> computeAsync(Callable<R> callable, ExecutorService executorService) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return callable.call();
			} catch (Exception ex) {
				LOG.error(ex.getMessage(), ex);
				throw new CompletionException(ex);
			}
		}, executorService);
	}

	/**
	 * Wrapper method over void tasks that ought to be run asynchronously. Handles logging of checked exceptions.
	 * @param callable Code to be executed
	 * @param executorService The ExecutorService
	 */
	public static CompletableFuture<Void> runAsync(Runnable callable, ExecutorService executorService) {
		return CompletableFuture.runAsync(() -> {
			try {
				callable.run();
			} catch (Exception ex) {
				LOG.error(ex.getMessage(), ex);
				throw new CompletionException(ex);
			}
		}, executorService);
	}

	/**
	 * Transforms Future<T> to CompletableFuture<T>
	 */
	public static <T> CompletableFuture<T> toCompletable(Future<T> future, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return future.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new CompletionException(e);
			}
		}, executor);
	}

}
