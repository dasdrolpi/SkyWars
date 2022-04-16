package de.drolpi.skywars.phase.collective.protection;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.kit.KitManager;
import de.drolpi.skywars.listener.protection.DamageProtectionListener;
import de.drolpi.skywars.phase.GamePhase;
import de.drolpi.skywars.phase.collective.ingame.InGamePhase;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class ProtectionPhase extends GamePhase {

    private final SkyWars skyWars;
    private final Server server;
    private final ProtectionCountdown countdown;

    public ProtectionPhase(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.server = skyWars.getPlugin().getServer();
        this.countdown = new ProtectionCountdown(skyWars, skyWars.getCounterConfig().getProtectionCounterInformation());
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
        return new InGamePhase(skyWars);
    }

    @Override
    public List<Listener> provideListener() {
        return Arrays.asList(new DamageProtectionListener(), skyWars.getDeathListener(), skyWars.getSpectatorListener());
    }
}
