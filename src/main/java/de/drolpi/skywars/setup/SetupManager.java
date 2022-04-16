package de.drolpi.skywars.setup;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.phase.PhasenManager;
import de.drolpi.skywars.phase.collective.setup.SetupPhase;
import de.drolpi.skywars.setup.listener.SetupListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.WeakHashMap;

public class SetupManager implements Listener {

    private final SkyWars skyWars;
    private final JavaPlugin javaPlugin;
    private final PhasenManager phasenManager;
    private final Map<Player, Setup> currentRunningSetups;

    public SetupManager(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.javaPlugin = skyWars.getPlugin();
        this.phasenManager = skyWars.getPhasenManager();
        this.currentRunningSetups = new WeakHashMap<>();
    }

    public void load() {
        javaPlugin.getServer().getPluginManager().registerEvents(new SetupListener(this), javaPlugin);
    }

    public void startSetup(Player player, Setup setup) {
        if(!currentRunningSetups.containsKey(player)) {
            currentRunningSetups.put(player, setup);
            phasenManager.setPhase(new SetupPhase(skyWars));
            setup.configure();
            setup.start();
        }
    }

    public SkyWars getSkyWars() {
        return skyWars;
    }

    public Map<Player, Setup> getCurrentRunningSetups() {
        return currentRunningSetups;
    }
}
