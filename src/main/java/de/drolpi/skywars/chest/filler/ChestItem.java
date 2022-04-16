package de.drolpi.skywars.chest.filler;

import de.drolpi.skywars.chest.bukkit.ConfigurationItemStack;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class ChestItem {

    private final int maxRandom, minRandom;
    private final ConfigurationItemStack itemStack;

    public ChestItem(int maxRandom, int minRandom, ConfigurationItemStack itemStack) {
        this.itemStack = itemStack;

        this.maxRandom = maxRandom;
        this.minRandom = minRandom;
    }

    public ItemStack getItemStack() {
        return itemStack.toNewItemStack();
    }

}
