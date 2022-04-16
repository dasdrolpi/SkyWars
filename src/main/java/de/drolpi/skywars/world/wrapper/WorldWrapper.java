package de.drolpi.skywars.world.wrapper;

import de.drolpi.skywars.world.LocalWorld;
import org.bukkit.World;

public interface WorldWrapper {

    String getWorldName();

    LocalWorld getWorld();

    World getBukkitWorld();

    boolean store();

    boolean load();

    void unload();

    boolean restoreFromSource();

    boolean isLoaded();

}
