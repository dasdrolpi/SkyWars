package de.drolpi.skywars.listener;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.mapvote.MapManager;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.world.LocalWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

    private final int maximumPlayers;
    private final String size;
    private final MapManager mapManager;

    public PingListener(SkyWars skyWars) {
        TeamRepository teamRepository = skyWars.getTeamRepository();
        int teams = teamRepository.getTeams().size();
        this.maximumPlayers = teamRepository.getTeams().stream().mapToInt(value -> value.getPlayers().max()).sum();
        this.size = teams + "x" + teamRepository.getTeams().stream().mapToInt(value -> value.getPlayers().max()).findAny().getAsInt();
        this.mapManager = skyWars.getMapManager();
    }

    @EventHandler
    public void handle(ServerListPingEvent event) {
        event.setMaxPlayers(maximumPlayers);
        String mapName = "Voting";
        LocalWorld world = mapManager.getCurrentWorld();
        if(world != null) {
            mapName = world.getName();
        }
        event.setMotd(size + " ● " + mapName);
    }

}
