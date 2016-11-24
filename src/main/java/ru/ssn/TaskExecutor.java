package ru.ssn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class TaskExecutor {


    public static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutor.class);

    private final Scheduler scheduler;

    private final ConcurrentNavigableMap<DateTime, List<Task>> tasks = new ConcurrentSkipListMap<>();

    public TaskExecutor(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addTask(Task task) {
        // Если время задачи больше текущего времени на заданное время, то сохраним эту задачу
        // иначе запустим задачу сразу.
        if (task.getDateTime().getTime() + 1000L > System.currentTimeMillis()) {
            store(task);
        } else {
            execute(task);
        }
    }

    private void execute(Task task) {
        Async.runAsync(new ExecuteAllOlderThanStrategy(task.getDateTime())).exceptionally(throwable -> {
            LOGGER.error("Task ends eith error", throwable);
            return null;
        }).thenAccept(aVoid -> LOGGER.info("Task {} complete successfully", task));
    }

    private void store(Task task) {
        DateTime dateTime = task.getDateTime();
        List<Task> absentList = this.tasks.putIfAbsent(dateTime, list(task));
        if (absentList != null) {
            // Кривое место!!! в случае если пока мы здесь tasks вычистится в executor'е, то потеряем текущий task.
            // Ок если DateTime приходят уникальные, но в таком случае value как List<Task> не нужны...
            absentList.add(task);
        } else {
            // если absentList == null, то это первый такой DateTime и надо поставить таймер.
            scheduler.schedule(task, () -> execute(task));
        }
    }

    private List<Task> list(Task task) {
        // использую synchronizedList, потому что не предполагаю частые его изменения.
        List<Task> list = Collections.synchronizedList(new ArrayList<>());
        list.add(task);
        return list;
    }

    public void stop() {
        scheduler.stop();
    }


    /**
     * Вычищает все задачи которые старше заданной{@code dateTime} и отправляет на обработку.
     */
    private class ExecuteAllOlderThanStrategy implements Callable<Void> {

        private final DateTime dateTime;

        ExecuteAllOlderThanStrategy(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        @Override
        public Void call() throws Exception {
            ConcurrentNavigableMap<DateTime, List<Task>> olderTasks = tasks.headMap(dateTime, true);

            List<CompletableFuture<String>> executedTasks = olderTasks.values().stream()
                    .flatMap(Collection::stream)
                    .map(Async::runTaskAsync)
                    .collect(Collectors.toList());

            Async.allOf(executedTasks).thenAccept(aVoid -> {
                LOGGER.info("{} period has beed processed", dateTime);
            });

            olderTasks.clear();

            return null;
        }
    }


}
