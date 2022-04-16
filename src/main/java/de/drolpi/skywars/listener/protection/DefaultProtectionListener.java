package de.drolpi.skywars.listener.protection;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public class DefaultProtectionListener implements Listener {

    @EventHandler
    public void handle(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory == null)
            return;
        if (inventory.getType().equals(InventoryType.ENCHANTING)) {
            inventory.setItem(1, getItem());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory == null)
            return;
        if (inventory.getType().equals(InventoryType.ENCHANTING)) {
            inventory.setItem(1, null);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if (inventory == null)
            return;
        if (inventory.getType().equals(InventoryType.ENCHANTING) && event.getSlot() == 1 && event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.INK_SACK)) {
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler
    public void onItemEnchantment(EnchantItemEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory == null)
            return;
        if (inventory.getType().equals(InventoryType.ENCHANTING)) {
            inventory.setItem(1, null);
            inventory.setItem(1, getItem());
        }
    }

    private ItemStack getItem() {
        Dye dye = new Dye();
        dye.setColor(DyeColor.BLUE);
        ItemStack itemStack = dye.toItemStack();
        itemStack.setAmount(64);
        return itemStack;
    }

}
