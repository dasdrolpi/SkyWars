package de.drolpi.skywars.phase;

import de.drolpi.skywars.SkyWars;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhasenManager {

    private final JavaPlugin javaPlugin;
    private final Map<GamePhase, List<Listener>> registeredListeners;
    private GamePhase currentPhase;

    public PhasenManager(SkyWars skyWars) {
        this.javaPlugin = skyWars.getPlugin();
        this.registeredListeners = new HashMap<>();
    }

    public void start(GamePhase firstPhase) {
        setPhase(firstPhase);
    }

    public void next() {
        if(currentPhase != null) {
            setPhase(currentPhase.next());
        }
    }

    public void setPhase(GamePhase gamePhase) {
        if(currentPhase != null) {
            this.currentPhase.disable();
            unregisterListener(currentPhase);
            this.currentPhase = null;
        }

        if(gamePhase != null) {
            this.currentPhase = gamePhase;
            this.currentPhase.enable();
            registerListener(currentPhase);
        }
    }

    private void registerListener(GamePhase gamePhase) {
        List<Listener> listenerSet = gamePhase.provideListener();

        if(listenerSet == null) return;

        registeredListeners.put(gamePhase, listenerSet);

        for (Listener listener : listenerSet) {
            javaPlugin.getServer().getPluginManager().registerEvents(listener, javaPlugin);
        }
    }

    private void unregisterListener(GamePhase gamePhase) {
        if(!registeredListeners.containsKey(gamePhase)) return;

        List<Listener> listenerSet = registeredListeners.get(gamePhase);
        registeredListeners.remove(gamePhase);

        for (Listener listener : listenerSet) {
            HandlerList.unregisterAll(listener);
        }
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public void handleJoin(Player player) {
        if(currentPhase != null) {
            currentPhase.playerJoin(player);
        }
    }

    public void handleQuit(Player player) {
        if(currentPhase != null) {
            currentPhase.playerQuit(player);
        }
    }

}