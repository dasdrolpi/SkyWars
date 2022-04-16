package de.drolpi.skywars.listener.protection;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.team.TeamRepository;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class SpectatorProtectionListener implements Listener {

    private final TeamRepository teamRepository;

    public SpectatorProtectionListener(SkyWars skyWars) {
        this.teamRepository = skyWars.getTeamRepository();
    }

    @EventHandler
    public void handle(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if(!(entity instanceof Player)) return;

        Player player = (Player) entity;

        if(!teamRepository.isInTeam(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if(!(entity instanceof Player)) return;

        Entity damager = event.getDamager();

        if(!(damager instanceof Player)) return;

        Player damagerPlayer = (Player) damager;

        if(!teamRepository.isInTeam(damagerPlayer)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(!teamRepository.isInTeam(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(!teamRepository.isInTeam(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if(!teamRepository.isInTeam(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if(!teamRepository.isInTeam(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(!teamRepository.isInTeam(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if(!teamRepository.isInTeam(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(FoodLevelChangeEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!teamRepository.isInTeam(player)) {
            event.setCancelled(true);
        }
    }
}
