package de.drolpi.skywars.phase.collective.setup;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.phase.GamePhase;
import de.drolpi.skywars.phase.collective.lobby.LobbyPhase;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class SetupPhase extends GamePhase {

    private final SkyWars skyWars;

    public SetupPhase(SkyWars skyWars) {
        this.skyWars = skyWars;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void playerJoin(Player player) {

    }

    @Override
    public void playerQuit(Player player) {

    }

    @Override
    public GamePhase next() {
        return new LobbyPhase(skyWars);
    }

    @Override
    public List<Listener> provideListener() {
        return new ArrayList<>();
    }
}
