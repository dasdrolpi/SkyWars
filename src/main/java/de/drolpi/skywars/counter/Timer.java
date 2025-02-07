package de.drolpi.skywars.counter;

import java.util.concurrent.TimeUnit;

public abstract class Timer extends Countdown {

    public Timer(int startTime, int stopTime, int tick, TimeUnit timeUnit) {
        super(stopTime, startTime + 1, tick, timeUnit);
    }

    @Override
    public int getCurrentTime() {
        return (stopTime - getTickedTime()) + startTime;
    }
}
