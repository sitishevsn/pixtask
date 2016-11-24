package ru.ssn;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public final class Async {

    public static <T> CompletableFuture<T> runAsync(Callable<T> callable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException("Error while executing", e);
            }
        });
    }

    public static CompletableFuture<String> runTaskAsync(Task task) {
        Callable<String> callable = task.getCallable();
        return runAsync(callable);
    }

    public static CompletableFuture<Void> allOf(List<CompletableFuture<String>> list) {
        return CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
    }


}
