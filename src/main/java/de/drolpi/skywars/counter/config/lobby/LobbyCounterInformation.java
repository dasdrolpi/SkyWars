package de.drolpi.skywars.counter.config.lobby;

import de.drolpi.skywars.counter.config.CounterInformation;
import de.drolpi.skywars.sound.SerializableSound;

public class LobbyCounterInformation extends CounterInformation {

    private final int playersToStart;
    private final ReduceInformation reduceInformation;

    public LobbyCounterInformation(int duration, boolean levelCount, boolean levelProgress, SerializableSound tickSound, SerializableSound readySound, int playersToStart, ReduceInformation reduceInformation) {
        super(duration, levelCount, levelProgress, tickSound, readySound);

        this.playersToStart = playersToStart;
        this.reduceInformation = reduceInformation;
    }

    public int getPlayersToStart() {
        return playersToStart;
    }

    public ReduceInformation getReduceInformation() {
        return reduceInformation;
    }
}
