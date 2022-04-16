package de.drolpi.skywars.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collection;

public class PlayerUtil {

    private PlayerUtil() {
        throw new UnsupportedOperationException("");
    }

    public static void resetPlayer(Player player) {
        player.setExp(0);
        player.setLevel(0);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.set
        player.setFireTicks(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setWalkSpeed(0.2F);
        player.spigot().setCollidesWithEntities(true);

        disableFly(player);
    }

    public static void clearPlayer(Player player) {
        PlayerInventory inventory = player.getInventory();

        inventory.clear();
        inventory.setHelmet(null);
        inventory.setChestplate(null);
        inventory.setLeggings(null);
        inventory.setBoots(null);
    }

    public static void enableFly(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    public static void disableFly(Player player) {
        player.setFlying(false);
        player.setAllowFlight(false);
    }

    public static void resetVisibility(Player player, Player player2) {
        player.showPlayer(player);
    }

    public static void resetVisibility(Player player, Collection<? extends Player> players) {
        for (Player player1 : players) {
            player.showPlayer(player1);
        }
    }

    public static void resetVisibility(Collection<? extends Player> players, Player player) {
        for (Player player1 : players) {
            player1.showPlayer(player);
        }
    }

    public static void resetVisibility(Collection<? extends Player> players) {
        for (Player player : players) {
            resetVisibility(player, players);
        }
    }
}
