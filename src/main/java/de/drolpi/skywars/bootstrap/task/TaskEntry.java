package de.drolpi.skywars.bootstrap.task;

import java.lang.reflect.Method;

public class TaskEntry {

    private final Object system;
    private final Task taskInfo;
    private final Method handler;

    public TaskEntry(Object system, Task taskInfo, Method handler) {
        this.system = system;
        this.taskInfo = taskInfo;
        this.handler = handler;
    }

    public Object getSystem() {
        return system;
    }

    public Task getTaskInfo() {
        return this.taskInfo;
    }

    public Method getHandler() {
        return this.handler;
    }

}