package de.drolpi.skywars.phase.collective.ending;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.listener.protection.DamageProtectionListener;
import de.drolpi.skywars.listener.protection.ProtectionListener;
import de.drolpi.skywars.phase.GamePhase;
import de.drolpi.skywars.phase.collective.restart.RestartPhase;
import de.drolpi.skywars.util.PlayerUtil;
import de.drolpi.skywars.game.GameManager;
import de.drolpi.skywars.world.WorldManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class EndingPhase extends GamePhase {

    private final SkyWars skyWars;
    private final Server server;
    private final EndingCountdown countdown;
    private final WorldManager worldManager;
    private final GameManager gameManager;

    public EndingPhase(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.server = skyWars.getPlugin().getServer();
        this.countdown = new EndingCountdown(skyWars, skyWars.getCounterConfig().getEndingCounterInformation());
        this.worldManager = skyWars.getWorldManager();
        this.gameManager = skyWars.getGameManager();
    }

    @Override
    public void enable() {
        countdown.start();
        gameManager.updateScoreboard();

        for (Player player : server.getOnlinePlayers()) {
            player.teleport(worldManager.getLobbyWorld().getSpawnLocation());
            gameManager.resetSpectators();
            PlayerUtil.resetPlayer(player);
            PlayerUtil.clearPlayer(player);
        }
    }

    @Override
    public void disable() {
        countdown.stop();
    }

    @Override
    public void playerJoin(Player player) {
        player.teleport(worldManager.getLobbyWorld().getSpawnLocation());
        gameManager.updateScoreboard();

        if(countdown.isRunning()) {
            player.setLevel(countdown.getCurrentTime());
            player.setExp(countdown.getCurrentTime() / (countdown.getStartTime() + 0.0F));
        }else {
            player.setLevel(countdown.getStartTime());
            player.setExp(1);
        }
    }

    @Override
    public void playerQuit(Player player) {
        gameManager.updateScoreboard();
    }

    @Override
    public GamePhase next() {
        return new RestartPhase(skyWars);
    }

    @Override
    public List<Listener> provideListener() {
        return Arrays.asList(new ProtectionListener(), new DamageProtectionListener());
    }
}
