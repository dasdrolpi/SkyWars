package de.drolpi.skywars.world;

import de.drolpi.skywars.location.SerializableLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;

import java.io.File;

public class LobbyWorld {

    private final String worldName;
    private SerializableLocation spawnLocation;
    private transient World world;

    public LobbyWorld(String worldName) {
        this.worldName = worldName;
    }

    public void setSpawnLocation(SerializableLocation spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Location getSpawnLocation() {
        return spawnLocation.toLocation();
    }

    public World getWorld() {
        return world;
    }

    public boolean load() {
        if(isLoaded()) return true;

        this.world = Bukkit.createWorld(
                new WorldCreator(worldName)
        );

        world.setGameRuleValue("randomTickSpeed", "0");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.getEntities().forEach(entity -> {
            if (!(entity instanceof ItemFrame) && !(entity instanceof ArmorStand)) {
                entity.remove();
            }
        });

        if(world != null) this.world.setAutoSave(false);
        return isLoaded();
    }

    public void unload() {
        if(world != null) Bukkit.unloadWorld(world, false);

        world = null;
    }

    public boolean isLoaded() {
        return world != null;
    }
}
