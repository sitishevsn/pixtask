package ru.ssn;

import java.util.concurrent.Callable;

public class Task {

    private final DateTime dateTime;
    private final Callable<String> callable;

    public Task(DateTime dateTime, Callable<String> callable) {
        this.dateTime = dateTime;
        this.callable = callable;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Callable<String> getCallable() {
        return callable;
    }


}
