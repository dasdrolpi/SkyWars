package de.drolpi.skywars.sound;

import lombok.Data;
import org.bukkit.Sound;

@Data
public class SerializableSound {

    private final Sound sound;
    private final float volume;
    private final float pitch;

    public SerializableSound() {
        this.sound = Sound.NOTE_STICKS;
        this.volume = 0;
        this.pitch = 1;
    }

    public SerializableSound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }
}
