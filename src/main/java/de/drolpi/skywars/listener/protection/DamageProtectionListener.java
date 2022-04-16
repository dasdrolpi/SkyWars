package de.drolpi.skywars.listener.protection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageProtectionListener implements Listener {

    @EventHandler
    public void handle(EntityDamageEvent event) {
        if(event.getCause() != EntityDamageEvent.DamageCause.VOID) {
            event.setCancelled(true);
        }
    }

}
