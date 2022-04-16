package de.drolpi.skywars.chest.bukkit;

import lombok.Getter;
import org.bukkit.enchantments.Enchantment;

@Getter
public class ConfigurationEnchantment {

    private final String name;
    private final int level;

    public ConfigurationEnchantment(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public ConfigurationEnchantment(Enchantment enchantment, int level) {
        this.name = enchantment.getName();
        this.level = level;
    }

    public Enchantment getEnchantment() {
        return Enchantment.getByName(name);
    }

}