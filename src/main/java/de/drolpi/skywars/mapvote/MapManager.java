package de.drolpi.skywars.mapvote;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.localization.MessageProvider;
import de.drolpi.skywars.location.SerializableLocation;
import de.drolpi.skywars.runnable.CatchingRunnable;
import de.drolpi.skywars.team.GameTeam;
import de.drolpi.skywars.world.LocalWorld;
import de.drolpi.skywars.world.WorldManager;
import de.drolpi.skywars.world.wrapper.LocalWorldWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Locale;

public class MapManager {

    private final JavaPlugin javaPlugin;
    private final WorldManager worldManager;
    private final MessageProvider messageProvider;

    private LocalWorldWrapper currentWorld;

    public MapManager(SkyWars skyWars) {
        this.javaPlugin = skyWars.getPlugin();
        this.worldManager = skyWars.getWorldManager();
        this.messageProvider = skyWars.getMessageProvider();
    }

    public void teleportTeams(Collection<GameTeam> teams) {
        Bukkit.getScheduler().runTask(javaPlugin, new CatchingRunnable(() -> {
            for (GameTeam team : teams) {
                Location spawn = getCurrentWorld().getSpawnForTeam(team).toLocation(currentWorld.getBukkitWorld());

                for (Player player : team.getPlayers().collect()) {
                    player.teleport(spawn);
                    player.setWalkSpeed(0);
                }
            }
        }));
    }

    public void endMapVoting() {
        currentWorld = worldManager.randomWorld();

        String prefix = messageProvider.getString(Locale.GERMAN, "skywars.prefix");
        for (String line : messageProvider.getStringArray(Locale.GERMAN, "skywars.lobby.countdown_map", getCurrentWorld().getName(), getCurrentWorld().getBuilder(), 0)) {
            for (Player player : javaPlugin.getServer().getOnlinePlayers()) {
                player.sendMessage(line.replaceAll("%prefix%", prefix));
            }
        }

    }

    public LocalWorldWrapper getCurrentWorldWrapper() {
        return currentWorld;
    }

    public LocalWorld getCurrentWorld() {
        if(currentWorld == null) {
            return null;
        }
        return currentWorld.getWorld();
    }
}
