package com.viteksu.kursach.core.parameters;

import java.util.LinkedList;
import java.util.List;

public class Property {
    private final List<String> resourses;
    private final int sleepTime;
    private final int sizeMessagePool;

    public Property(List<String> resourses, int sleepTime, int sizeMessagePool) {
        this.resourses = resourses;
        this.sleepTime = sleepTime;
        this.sizeMessagePool = sizeMessagePool;
    }

    public List<String> getResourses() {
        return resourses;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public int getSizeMessagePool() {
        return sizeMessagePool;
    }
}
