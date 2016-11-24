package ru.ssn;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {

    private Timer timer = new Timer();

    public void schedule(Task task, Runnable runnable) {
        long time = task.getDateTime().getTime();
        long delay = time - System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay);
    }

    public void stop() {
        timer.cancel();
    }
}
