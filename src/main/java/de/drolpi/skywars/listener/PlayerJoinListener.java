package de.drolpi.skywars.listener;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.phase.PhasenManager;
import de.drolpi.skywars.setup.SetupManager;
import de.drolpi.skywars.setup.WorldSetup;
import de.drolpi.skywars.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final SkyWars skyWars;
    private final PhasenManager phasenManager;
    private final SetupManager setupManager;

    public PlayerJoinListener(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.phasenManager = skyWars.getPhasenManager();
        this.setupManager = skyWars.getSetupManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);
        PlayerUtil.resetPlayer(player);
        PlayerUtil.clearPlayer(player);
        PlayerUtil.resetVisibility(Bukkit.getOnlinePlayers());

        phasenManager.handleJoin(player);
    }
}
