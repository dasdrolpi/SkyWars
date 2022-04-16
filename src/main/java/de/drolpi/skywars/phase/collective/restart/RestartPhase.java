package de.drolpi.skywars.phase.collective.restart;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.config.SkyWarsConfig;
import de.drolpi.skywars.phase.GamePhase;
import de.drolpi.skywars.runnable.CatchingRunnable;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class RestartPhase extends GamePhase {

    private final SkyWars skyWars;
    private final Server server;
    private final SkyWarsConfig.RestartMethod restartMethod;

    public RestartPhase(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.server = skyWars.getPlugin().getServer();
        this.restartMethod = skyWars.getSkyWarsConfig().getRestartMethod();
    }

    @Override
    public void enable() {
        server.getScheduler().runTask(skyWars.getPlugin(), new CatchingRunnable(() -> {
            restartMethod.run(server);
        }));
    }

    @Override
    public void disable() {
    }

    @Override
    public void playerJoin(Player player) {
    }

    @Override
    public void playerQuit(Player player) {
    }

    @Override
    public GamePhase next() {
        return null;
    }

    @Override
    public List<Listener> provideListener() {
        return new ArrayList<>();
    }
}
