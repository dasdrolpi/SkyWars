package de.drolpi.skywars.chest.filler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;

@AllArgsConstructor
@Getter
public enum ChestType {

    NONE,
    NORMAL,
    MIDDLE;

    public static ChestType getTypeByLocation(Block block) {
        if(block.getType().equals(Material.CHEST)) {
            return NORMAL;
        }
        if(block.getType().equals(Material.TRAPPED_CHEST)) {
            return MIDDLE;
        }

        return NONE;
    }

}
