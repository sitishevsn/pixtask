package ru.ssn;

import ru.ssn.fixtures.TaskFixtures;

import java.util.List;

public class MultipleTaskTest {
    public static void main(String[] args) {

        TaskExecutor executor = new TaskExecutor(new Scheduler());

        List<Task> tasks = TaskFixtures.randomMultipleTasks();
        tasks.forEach(executor::addTask);


//        executor.stop();


    }
}
