package ru.ssn.fixtures;

import ru.ssn.DateTime;
import ru.ssn.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TaskFixtures {

    public static List<Task> serialSingleTasks() {
        List<Task> tasks = new ArrayList<>();

        long now = System.currentTimeMillis();

        tasks.add(task(time(now + 1000L), callable("callable 1")));
        tasks.add(task(time(now + 2000L), callable("callable 2")));
        tasks.add(task(time(now + 3000L), callable("callable 3")));
        tasks.add(task(time(now + 4000L), callable("callable 4")));

        return tasks;
    }


    public static List<Task> randomMultipleTasks() {
        List<Task> tasks = new ArrayList<>();

        long now = System.currentTimeMillis();

        tasks.add(task(time(now + 5000L), callable("callable 1.1")));
        tasks.add(task(time(now + 5000L), callable("callable 1.2")));

        tasks.add(task(time(now + 6000L), callable("callable 2.1")));
        tasks.add(task(time(now + 6000L), callable("callable 2.2")));
        tasks.add(task(time(now + 6000L), callable("callable 2.3")));

        tasks.add(task(time(now + 7000L), callable("callable 3.1")));
        tasks.add(task(time(now + 7000L), callable("callable 3.2")));
        tasks.add(task(time(now + 7000L), callable("callable 3.3")));
        tasks.add(task(time(now + 7000L), callable("callable 3.4")));

        tasks.add(task(time(now + 8000L), callable("callable 4.1")));

        tasks.add(task(time(now + 500L), callable("callable 0")));

        return tasks;
    }

    private static Task task(DateTime time, Callable<String> callable) {
        return new Task(time, callable);
    }

    private static DateTime time(long time) {
        return new DateTime(time);
    }

    private static Callable<String> callable(String value) {
        return () -> {
            System.out.println("Collable " + value + " processed");
            return value;
        };
    }

}
