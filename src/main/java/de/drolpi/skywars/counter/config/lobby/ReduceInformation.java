package de.drolpi.skywars.counter.config.lobby;

import lombok.Data;

@Data
public class ReduceInformation {

    private final int reduceTime;
    private final int playersToReduce;

    public int getReduceTime() {
        return reduceTime + 1;
    }
}
