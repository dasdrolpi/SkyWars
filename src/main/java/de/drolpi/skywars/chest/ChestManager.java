package de.drolpi.skywars.chest;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.chest.filler.ChestFiller;
import de.drolpi.skywars.chest.filler.ChestType;
import de.drolpi.skywars.mapvote.MapManager;
import de.drolpi.skywars.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChestManager {

    private final ChestFiller chestFiller;
    //private final Map<Location, Inventory> cachedInventories;
    private final MapManager mapManager;

    public ChestManager(SkyWars skyWars) {
        this.chestFiller = new ChestFiller();
        //this.cachedInventories = new HashMap<>();
        this.mapManager = skyWars.getMapManager();
    }

    public void loadChests() {
        for (Chunk loadedChunk : Bukkit.getWorld(mapManager.getCurrentWorldWrapper().getBukkitWorld().getName()).getLoadedChunks()) {
            for (BlockState tileEntity : loadedChunk.getTileEntities()) {
                switch (tileEntity.getType()) {
                    case CHEST: {
                        Chest chest = (Chest) tileEntity.getBlock().getState();
                        Inventory inventory = chest.getBlockInventory();
                        inventory.clear();
                        chestFiller.fillChest(ChestType.NORMAL, inventory);
                        break;
                    }

                    case TRAPPED_CHEST: {
                        tileEntity.getBlock().setType(Material.CHEST);
                        Chest chest = (Chest) tileEntity.getBlock().getState();
                        Inventory inventory = chest.getBlockInventory();
                        inventory.clear();

                        chestFiller.fillChest(ChestType.MIDDLE, inventory);
                        break;
                    }

                    default:
                        break;
                }
            }
        }
    }

    /*
    public void openChest(Player player, ChestGetType chestGetType, Block block) {
        Inventory cache = getChest(block.getLocation());
        Inventory chest = cache != null ? cache : createBukkitChest(block);

        if (chestGetType == ChestGetType.OPEN) {
            player.openInventory(chest);
            player.playSound(block.getLocation(), Sound.CHEST_OPEN, 1, 1);
            return;
        }

        World world = block.getLocation().getWorld();

        for (Player viewingPlayer : getViewingPlayer(chest)) {
            viewingPlayer.closeInventory();
        }

        for (ItemStack content : chest.getContents()) {
            if (content != null) {
                world.dropItemNaturally(block.getLocation(), content);
            }
        }
    }

    public Inventory createBukkitChest(Block block) {
        Inventory chest = Bukkit.createInventory(null, 9*3);

        ChestType chestType = ChestType.getTypeByLocation(block);
        chest = chestFiller.fillChest(chestType, chest);

        cachedInventories.put(block.getLocation(), chest);
        return chest;
    }

    public Inventory getChest(Location location) {
        return cachedInventories.get(location);
    }

    public Set<Player> getViewingPlayer(Inventory chest) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.getOpenInventory().getTopInventory().equals(chest)).collect(Collectors.toSet());
    }

     */
}
