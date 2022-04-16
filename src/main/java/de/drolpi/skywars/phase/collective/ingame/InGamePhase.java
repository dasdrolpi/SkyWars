package de.drolpi.skywars.phase.collective.ingame;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.listener.PlayerDeathListener;
import de.drolpi.skywars.phase.GamePhase;
import de.drolpi.skywars.phase.collective.ending.EndingPhase;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InGamePhase extends GamePhase {

    private final SkyWars skyWars;
    private final Server server;
    private final InGameCountdown countdown;

    public InGamePhase(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.server = skyWars.getPlugin().getServer();
        this.countdown = new InGameCountdown(skyWars, skyWars.getCounterConfig().getInGameCounterInformation());
    }

    @Override
    public void enable() {
        countdown.start();
    }

    @Override
    public void disable() {
        countdown.stop();
    }

    @Override
    public void playerJoin(Player player) {

    }

    @Override
    public void playerQuit(Player player) {

    }

    @Override
    public GamePhase next() {
        return new EndingPhase(skyWars);
    }

    @Override
    public List<Listener> provideListener() {
        return Arrays.asList(skyWars.getDeathListener(), skyWars.getSpectatorListener());
    }
}
