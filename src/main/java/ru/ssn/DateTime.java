package ru.ssn;

public class DateTime implements Comparable<DateTime> {

    private final long time;

    public DateTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateTime dateTime = (DateTime) o;

        return time == dateTime.time;

    }

    @Override
    public int hashCode() {
        return (int) (time ^ (time >>> 32));
    }

    @Override
    public int compareTo(DateTime o) {
        return Long.compare(time, o.getTime());
    }

    @Override
    public String toString() {
        return "DateTime{" +
                "time=" + time +
                '}';
    }
}