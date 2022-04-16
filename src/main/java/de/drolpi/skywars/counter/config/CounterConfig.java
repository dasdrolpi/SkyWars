package de.drolpi.skywars.counter.config;

import de.drolpi.skywars.counter.config.lobby.LobbyCounterInformation;
import de.drolpi.skywars.counter.config.lobby.ReduceInformation;
import de.drolpi.skywars.sound.SerializableSound;
import lombok.Data;
import org.bukkit.Sound;

import java.util.HashMap;
import java.util.Map;

@Data
public class CounterConfig {

    private final LobbyCounterInformation lobbyCounterInformation;
    private final CounterInformation startingCounterInformation;
    private final CounterInformation protectionCounterInformation;
    private final CounterInformation inGameCounterInformation;
    private final CounterInformation endingCounterInformation;

    public CounterConfig() {
        lobbyCounterInformation = new LobbyCounterInformation(60, true, true, new SerializableSound(Sound.NOTE_PLING, 1, 1), new SerializableSound(Sound.NOTE_PLING, 2, 3), 2, new ReduceInformation(20, 4));
        startingCounterInformation = new CounterInformation(5, true, true, new SerializableSound(Sound.NOTE_PLING, 1, 1), new SerializableSound(Sound.NOTE_PLING, 2, 3));
        protectionCounterInformation = new CounterInformation(20, false, false, new SerializableSound(), new SerializableSound());
        inGameCounterInformation = new CounterInformation(60, false, false, new SerializableSound(Sound.NOTE_PLING, 1, 1), new SerializableSound(Sound.NOTE_BASS, 1, 1));
        endingCounterInformation = new CounterInformation(15, true, true, new SerializableSound(), new SerializableSound());
    }
}
