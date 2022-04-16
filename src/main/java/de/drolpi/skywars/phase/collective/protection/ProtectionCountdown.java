package de.drolpi.skywars.phase.collective.protection;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.counter.Countdown;
import de.drolpi.skywars.counter.Counter;
import de.drolpi.skywars.counter.config.CounterInformation;
import de.drolpi.skywars.localization.MessageProvider;
import de.drolpi.skywars.phase.PhasenManager;
import de.drolpi.skywars.sound.SerializableSound;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ProtectionCountdown extends Countdown {

    private final Server server;
    private final PhasenManager phasenManager;
    private final MessageProvider messageProvider;
    private final SerializableSound tickSound;
    private final SerializableSound readySound;
    private final boolean levelCount;
    private final boolean levelProgress;

    public ProtectionCountdown(SkyWars skyWars, CounterInformation information) {
        super(information.getDuration(), 0, 1, TimeUnit.SECONDS);

        this.server = skyWars.getPlugin().getServer();
        this.phasenManager = skyWars.getPhasenManager();
        this.messageProvider = skyWars.getMessageProvider();
        this.tickSound = information.getTickSound();
        this.readySound = information.getReadySound();
        this.levelCount = information.isLevelCount();
        this.levelProgress = information.isLevelProgress();
    }

    @Override
    public void onStart(Counter counter) {

    }

    @Override
    public void onTick(Counter counter) {
        switch (counter.getCurrentTime()) {
            case 20: case 15: case 10: case 5: case 4: case 3: case 2: case 1:
                for (Player player : server.getOnlinePlayers()) {
                    player.sendMessage(
                            messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                                    + messageProvider.getString(Locale.GERMAN, "skywars.protection.countdown_tick", counter.getCurrentTime())
                    );
                    player.playSound(player.getLocation(), tickSound.getSound(), tickSound.getVolume(), tickSound.getPitch());
                }
                break;
            default:
                break;

        }

        if(levelCount || levelProgress) {
            for (Player player : server.getOnlinePlayers()) {
                if(levelCount) {
                    player.setLevel(counter.getCurrentTime());
                }
                if(levelProgress) {
                    player.setExp(counter.getCurrentTime() / (getStartTime() + 0.0F));
                }
            }
        }
    }

    @Override
    public void onFinish(Counter counter) {
        for (Player player : server.getOnlinePlayers()) {
            player.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                            + messageProvider.getString(Locale.GERMAN, "skywars.protection.countdown_finish")
            );
            player.playSound(player.getLocation(), readySound.getSound(), readySound.getVolume(), readySound.getPitch());
            if(levelCount) {
                player.setLevel(0);
            }
            if(levelProgress) {
                player.setExp(0);
            }
        }
        phasenManager.next();
    }

    @Override
    public void onCancel(Counter counter) {
        for (Player player : server.getOnlinePlayers()) {
            if(levelCount) {
                player.setLevel(getStartTime());
            }
            if(levelProgress) {
                player.setExp(1);
            }
        }
    }
}
