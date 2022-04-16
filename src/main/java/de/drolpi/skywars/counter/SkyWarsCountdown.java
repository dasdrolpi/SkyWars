package de.drolpi.skywars.counter;

import de.drolpi.skywars.bootstrap.SkyWarsPlugin;
import de.drolpi.skywars.counter.config.CounterInformation;
import de.drolpi.skywars.runnable.CatchingRunnable;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public abstract class SkyWarsCountdown extends Countdown {

    private final SkyWarsPlugin plugin;
    private final int playersToStart, playersToReduce, reduceTime;

    public SkyWarsCountdown(SkyWarsPlugin plugin, int startTime, int stopTime, int tick, TimeUnit timeUnit, int playersToStart, int playersToReduce, int reduceTime) {
        super(startTime, stopTime, tick, timeUnit);
        this.plugin = plugin;
        this.playersToStart = playersToStart;
        this.playersToReduce = playersToReduce;
        this.reduceTime = reduceTime;
    }

    public void handleJoin(Collection<? extends Player> players) {
        if(players.size() >= playersToStart) {
            if(isPaused()) {
                resume();
            }else if (!isRunning()){
                start();
            }
        }

        if(players.size() >= playersToReduce) {
            if (getCurrentTime() > reduceTime) {
                setCurrentTime(reduceTime);
            }
        }
    }

    public void handleQuit(Collection<? extends Player> players) {
        plugin.getServer().getScheduler().runTaskLater(plugin, new CatchingRunnable(() -> {
            if (players.size() <= (playersToStart - 1)) {
                if (!isPaused() && isRunning()) {
                    stop();
                }
            }
        }), 5);
    }
}
