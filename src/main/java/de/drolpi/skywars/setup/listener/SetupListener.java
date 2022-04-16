package de.drolpi.skywars.setup.listener;

import de.drolpi.skywars.setup.Setup;
import de.drolpi.skywars.setup.SetupManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SetupListener implements Listener {

    private final SetupManager setupManager;

    public SetupListener(SetupManager setupManager) {
        this.setupManager = setupManager;
    }

    @EventHandler
    public void handle(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("skywars.setup") || !setupManager.getCurrentRunningSetups().containsKey(player)) {
            return;
        }

        event.setCancelled(true);
        player.sendMessage(event.getMessage());

        Setup setup = setupManager.getCurrentRunningSetups().get(player);
        setup.apply(player, event.getMessage());
    }

}
