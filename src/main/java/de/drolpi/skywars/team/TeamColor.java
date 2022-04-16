package de.drolpi.skywars.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

@AllArgsConstructor
@Getter
public enum TeamColor {

    RED(ChatColor.RED, DyeColor.RED),
    BLUE(ChatColor.BLUE, DyeColor.BLUE),
    YELLOW(ChatColor.YELLOW, DyeColor.YELLOW),
    GREEN(ChatColor.DARK_GREEN, DyeColor.GREEN),
    PINK(ChatColor.LIGHT_PURPLE, DyeColor.PINK),
    LIGHT_BLUE(ChatColor.AQUA, DyeColor.LIGHT_BLUE),
    LIGHT_GREEN(ChatColor.GREEN, DyeColor.LIME),
    ORANGE(ChatColor.GOLD, DyeColor.ORANGE);

    private final transient ChatColor chatColor;
    private final transient DyeColor dyeColor;

}
