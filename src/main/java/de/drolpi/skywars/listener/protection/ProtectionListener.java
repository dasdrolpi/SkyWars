package de.drolpi.skywars.listener.protection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ProtectionListener implements Listener {

    @EventHandler
    public void handle(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
