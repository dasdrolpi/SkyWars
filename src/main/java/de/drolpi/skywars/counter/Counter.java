package de.drolpi.skywars.counter;

import de.drolpi.skywars.counter.event.CountEventProvider;
import java.util.concurrent.TimeUnit;

public interface Counter {

    void start();

    void pause();

    void resume();

    void stop();

    int getCurrentTime();

    boolean isPaused();

    void setPaused(boolean paused);

    boolean isRunning();

    void setRunning(boolean running);

    interface Builder {

        Builder startTime(int startTime);

        Builder stopTime(int stopTime);

        Builder tick(int tick);

        Builder timeUnit(TimeUnit timeUnit);

        Builder eventProvider(CountEventProvider eventProvider);

        Counter createTimer();

        Counter createCountdown();

    }

}
