package de.drolpi.skywars.world.wrapper;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.team.GameTeam;
import de.drolpi.skywars.team.TeamColor;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.util.FileUtil;
import de.drolpi.skywars.world.LocalWorld;
import de.drolpi.skywars.world.island.Island;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LocalWorldWrapper implements WorldWrapper {

    protected final LocalWorld world;
    protected final File sourceWorldFolder;
    protected final TeamRepository teamRepository;
    private File activeWorldFolder;
    private World bukkitWorld;

    public LocalWorldWrapper(LocalWorld world, SkyWars skyWars, boolean loadOnInit) {
        this.world = world;
        this.teamRepository = skyWars.getTeamRepository();
        this.sourceWorldFolder = new File(
                new File(skyWars.getPlugin().getDataFolder(), "worlds"),
                world.getWorldName()
        );

        if (loadOnInit) load();
    }

    @Override
    public boolean store() {
        try {
            World bukkitWorld = Bukkit.getWorld(world.getWorldName());
            bukkitWorld.save();

            FileUtil.copy(
                    new File(Bukkit.getWorldContainer().getParentFile(), world.getWorldName()),
                    sourceWorldFolder
            );
            return true;
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to load GameMap from original folder " + sourceWorldFolder.getName() + " to backup folder " + activeWorldFolder.getName());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getWorldName() {
        return world.getWorldName();
    }

    @Override
    public LocalWorld getWorld() {
        return world;
    }

    @Override
    public World getBukkitWorld() {
        return bukkitWorld;
    }

    @Override
    public boolean load() {
        if (isLoaded()) return true;
        if (!shouldLoad()) return false;

        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer().getParentFile(),
                sourceWorldFolder.getName()
        );

        try {
            FileUtil.copy(sourceWorldFolder, activeWorldFolder);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to load GameMap from backup folder " + sourceWorldFolder.getName() + " to active folder " + activeWorldFolder.getName());
            e.printStackTrace();
            return false;
        }

        this.bukkitWorld = Bukkit.createWorld(
                new WorldCreator(activeWorldFolder.getName())
        );

        bukkitWorld.setGameRuleValue("randomTickSpeed", "0");
        bukkitWorld.setGameRuleValue("doDaylightCycle", "false");
        bukkitWorld.setGameRuleValue("doMobSpawning", "false");
        bukkitWorld.getEntities().forEach(entity -> {
            if (!(entity instanceof ItemFrame) && !(entity instanceof ArmorStand)) {
                entity.remove();
            }
        });

        if (bukkitWorld != null) this.bukkitWorld.setAutoSave(false);
        return isLoaded();
    }

    @Override
    public void unload() {
        if (bukkitWorld != null) Bukkit.unloadWorld(bukkitWorld, false);
        if (activeWorldFolder != null) FileUtil.delete(activeWorldFolder);

        bukkitWorld = null;
        activeWorldFolder = null;
    }

    @Override
    public boolean restoreFromSource() {
        unload();
        return load();
    }

    @Override
    public boolean isLoaded() {
        return getBukkitWorld() != null;
    }

    protected boolean shouldLoad() {
        Set<TeamColor> teams = teamRepository.getTeams().stream().map(GameTeam::getColor).collect(Collectors.toSet());
        Set<TeamColor> worldTeams = new HashSet<>(teams);
        if (world.getIslands().size() != teams.size()) {
            System.out.println(
                    "The world (" + getWorldName() + "/" + world.getName() + ") could not be loaded, because the team size does not match"
            );
            return false;
        }

        for (Island island : world.getIslands()) {
            worldTeams.remove(island.getTeamColor());
        }

        if (worldTeams.size() == world.getIslands().size()) {
            System.out.println(
                    "The world (" + getWorldName() + "/" + world.getName() + ") could not be loaded, because the team size does not match"
            );
            return false;
        }
        return true;
    }
}
