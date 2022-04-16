package de.drolpi.skywars.kit;

import de.drolpi.skywars.chest.bukkit.ConfigurationItemStack;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

@Data
public class Kit {

    private final String name;
    private ConfigurationItemStack selectItem;
    private ConfigurationItemStack helmet;
    private ConfigurationItemStack chestplate;
    private ConfigurationItemStack leggins;
    private ConfigurationItemStack boots;
    private Map<Integer, ConfigurationItemStack> inventory;

    public Kit(String name) {
        this.name = name;
    }

    public void giveKit(Player player) {
        PlayerInventory inventory = player.getInventory();

        if(helmet != null) inventory.setHelmet(helmet.toNewItemStack());
        if(chestplate != null) inventory.setChestplate(chestplate.toNewItemStack());
        if(leggins != null) inventory.setLeggings(leggins.toNewItemStack());
        if(boots != null) inventory.setBoots(boots.toNewItemStack());

        if (this.inventory != null) {
            for (Map.Entry<Integer, ConfigurationItemStack> entry : this.inventory.entrySet()) {
                inventory.setItem(entry.getKey(), entry.getValue().toNewItemStack());
            }
        }
    }
}
