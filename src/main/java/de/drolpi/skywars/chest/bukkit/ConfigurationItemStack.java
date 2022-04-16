package de.drolpi.skywars.chest.bukkit;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ConfigurationItemStack {

    private final Material material;
    private int amount;
    private short subId;
    private String name;
    private final List<String> lore;
    private final List<ConfigurationEnchantment> enchantments;

    public ConfigurationItemStack(Material material) {
        this.material = material;
        this.amount = 0;
        this.subId = 0;
        this.name = "";
        this.lore = new ArrayList<>();
        this.enchantments = new ArrayList<>();
    }

    public static Builder builder(Material material) {
        return new Builder(material);
    }

    public ItemStack toNewItemStack() {
        ItemStack itemStack = new ItemStack(material, amount, subId);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);

        for (ConfigurationEnchantment enchantment : enchantments) {
            itemMeta.addEnchant(enchantment.getEnchantment(), enchantment.getLevel(), true);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ConfigurationItemStack of(ItemStack itemStack) {
        return ConfigurationItemStack
                .builder(itemStack.getType())
                .setAmount(itemStack.getAmount())
                .setSubId(itemStack.getDurability())
                .build();
    }

    public static class Builder {

        private final ConfigurationItemStack itemStack;

        public Builder(Material material) {
            this.itemStack = new ConfigurationItemStack(material);
        }

        public Builder setAmount(int amount) {
            itemStack.amount = amount;
            return this;
        }

        public Builder setSubId(short subId) {
            itemStack.subId = subId;
            return this;
        }

        public Builder setName(String name) {
            itemStack.name = name;
            return this;
        }

        public Builder addLore(String lore) {
            itemStack.lore.add(lore);
            return this;
        }

        public Builder addEnchantment(Enchantment enchantment, int level) {
            itemStack.enchantments.add(new ConfigurationEnchantment(enchantment, level));
            return this;
        }

        public ConfigurationItemStack build() {
            return itemStack;
        }

    }

}