package de.drolpi.skywars.kit.config;

import de.drolpi.skywars.chest.bukkit.ConfigurationItemStack;
import de.drolpi.skywars.kit.Kit;
import lombok.Data;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class KitConfig {

    private final String defaultKitName;
    private final Set<Kit> kits;

    public KitConfig() {
        this.defaultKitName = "Starter";
        this.kits = new HashSet<>();
        Kit startKit = new Kit("Starter");
        startKit.setSelectItem(ConfigurationItemStack.builder(Material.IRON_PICKAXE).setAmount(1).build());
        startKit.setHelmet(ConfigurationItemStack.builder(Material.CHAINMAIL_HELMET).setAmount(1).build());
        startKit.setChestplate(ConfigurationItemStack.builder(Material.CHAINMAIL_CHESTPLATE).setAmount(1).build());
        startKit.setLeggins(ConfigurationItemStack.builder(Material.CHAINMAIL_LEGGINGS).setAmount(1).build());
        startKit.setBoots(ConfigurationItemStack.builder(Material.CHAINMAIL_BOOTS).setAmount(1).build());
        Map<Integer, ConfigurationItemStack> inventory = new HashMap<>();

        inventory.put(0, ConfigurationItemStack.builder(Material.IRON_SWORD).setAmount(1).build());
        inventory.put(1, ConfigurationItemStack.builder(Material.IRON_PICKAXE).setAmount(1).build());

        startKit.setInventory(inventory);
        kits.add(startKit);
    }

    public Set<Kit> getKits() {
        return kits;
    }
}
