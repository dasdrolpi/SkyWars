package de.drolpi.skywars.kills;

import de.drolpi.skywars.SkyWars;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.WeakHashMap;

public class KillerManager {

    private JavaPlugin plugin;
    private final Server server;
    private final Map<Player, HitInformation> hitInformation;
    private final KillerListener killerListener;

    public KillerManager(SkyWars skyWars) {
        this.plugin = skyWars.getPlugin();
        this.server = plugin.getServer();
        this.hitInformation = new WeakHashMap<>();
        this.killerListener = new KillerListener(this);
    }

    public void load() {
        plugin.getServer().getPluginManager().registerEvents(killerListener, plugin);
    }

    public void createPlayers() {
        for (Player player : server.getOnlinePlayers()) {
            createPlayer(player);
        }
    }

    public void createPlayer(Player player) {
        hitInformation.put(player, new HitInformation(player));
    }

    public void removePlayer(Player player) {
        hitInformation.remove(player);
        for (HitInformation value : hitInformation.values()) {
            if(value.getLastDamager() != null && value.getLastDamager().equals(player)) {
                value.clear();
            }
        }
    }

    public HitInformation getHitInformation(Player player) {
        return hitInformation.get(player);
    }
}
