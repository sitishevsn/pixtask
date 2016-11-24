package ru.ssn;

import ru.ssn.fixtures.TaskFixtures;

import java.util.List;

public class TaskExecutorTest {

    public static void main(String[] args) {

        TaskExecutor executor = new TaskExecutor(new Scheduler());

        List<Task> tasks = TaskFixtures.serialSingleTasks();
        tasks.forEach(executor::addTask);


//        executor.stop();


    }

}
