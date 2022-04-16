package de.drolpi.skywars.phase;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class GamePhase {

    public abstract void enable();

    public abstract void disable();

    public abstract void playerJoin(Player player);

    public abstract void playerQuit(Player player);

    public abstract GamePhase next();

    public abstract List<Listener> provideListener();

}
