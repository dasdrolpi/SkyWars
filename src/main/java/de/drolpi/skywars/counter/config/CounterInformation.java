package de.drolpi.skywars.counter.config;

import de.drolpi.skywars.sound.SerializableSound;
import lombok.Data;

@Data
public class CounterInformation {

    private final int duration;
    private final boolean levelCount;
    private final boolean levelProgress;
    private final SerializableSound tickSound;
    private final SerializableSound readySound;

}
