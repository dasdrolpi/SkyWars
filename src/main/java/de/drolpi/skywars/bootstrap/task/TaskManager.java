package de.drolpi.skywars.bootstrap.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskManager {

    private final Object system;
    private final EnumMap<LifeCycle, List<TaskEntry>> tasks = new EnumMap<>(LifeCycle.class);
    private LifeCycle lifeCycle;

    public TaskManager(Object system) {
        this.system = system;
        this.lifeCycle = LifeCycle.UNUSEABLE;

        for (LifeCycle moduleLifeCycle : LifeCycle.values()) {
            this.tasks.put(moduleLifeCycle, new CopyOnWriteArrayList<>());
        }
    }

    public void loadMethods() {
        for (Method method : system.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() == 0 && method.isAnnotationPresent(Task.class)) {
                Task task = method.getAnnotation(Task.class);
                this.tasks.get(task.event()).add(new TaskEntry(system, task, method));
            }
        }
    }

    public LifeCycle getCurrentLifeCycle() {
        return lifeCycle;
    }

    public void fireTasks(LifeCycle lifeCycle) {
        if(this.fireTasks(this.tasks.get(lifeCycle))) {
            this.lifeCycle = lifeCycle;
        }
    }

    public void clearTasks() {
        lifeCycle = null;
        tasks.clear();
    }

    private boolean fireTasks(List<TaskEntry> entries) {
        entries.sort(Comparator.comparingInt(o -> o.getTaskInfo().order()));

        for (TaskEntry entry : entries) {
            entry.getHandler().setAccessible(true);
            try {
                entry.getHandler().invoke(entry.getSystem());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        return true;
    }
}