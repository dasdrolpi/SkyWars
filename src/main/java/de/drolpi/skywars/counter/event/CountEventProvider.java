package de.drolpi.skywars.counter.event;

import de.drolpi.skywars.counter.Counter;

public interface CountEventProvider {

    void onStart(Counter counter);

    void onTick(Counter counter);

    void onFinish(Counter counter);

    void onCancel(Counter counter);

}
