package de.drolpi.skywars.listener;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.kills.KillerManager;
import de.drolpi.skywars.phase.PhasenManager;
import de.drolpi.skywars.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PhasenManager phasenManager;
    private final GameManager gameManager;
    private final KillerManager killerManager;

    public PlayerQuitListener(SkyWars skyWars) {
        this.phasenManager = skyWars.getPhasenManager();
        this.gameManager = skyWars.getGameManager();
        this.killerManager = skyWars.getKillerManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(null);
        phasenManager.handleQuit(player);

        gameManager.removeSpectator(player);
        killerManager.removePlayer(player);
    }

    @EventHandler
    public void handleKick(PlayerKickEvent event) {
        event.setLeaveMessage(null);
    }
}
