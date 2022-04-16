package de.drolpi.skywars.kills;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class KillerListener implements Listener {

    private final KillerManager killerManager;

    public KillerListener(KillerManager killerManager) {
        this.killerManager = killerManager;
    }

    @EventHandler
    public void handleDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            final HitInformation hitInformation = killerManager.getHitInformation(player);

            if(hitInformation == null)
                return;

            if (event.getDamager() instanceof Player) {
                final Player damager = (Player) event.getDamager();

                if(self(player, damager)) return;

                hitInformation.setLastDamager(damager, System.currentTimeMillis());
            }

            if (event.getDamager() instanceof Projectile) {
                final Projectile projectile = (Projectile) event.getDamager();

                if (projectile.getShooter() instanceof Player) {
                    final Player damager = (Player) projectile.getShooter();

                    if(self(player, damager)) return;

                    hitInformation.setLastDamager(damager, System.currentTimeMillis());
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerFish(PlayerFishEvent event){
        final Player damager = event.getPlayer();

        if(event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY && event.getCaught() instanceof Player){
            final Player player = (Player)event.getCaught();

            if(self(player, damager)) return;

            final HitInformation hitInformation = killerManager.getHitInformation(player);

            hitInformation.setLastDamager(damager, System.currentTimeMillis());
        }
    }

    private boolean self(Entity e1, Entity e2) {
        return e1 == e2;
    }

}
