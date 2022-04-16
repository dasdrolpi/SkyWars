package de.drolpi.skywars.world.island;

import de.drolpi.skywars.location.SerializableLocation;
import de.drolpi.skywars.team.TeamColor;
import org.bukkit.Location;

public class Island {

    private final TeamColor teamColor;
    private final SerializableLocation spawnLocation;

    public Island(TeamColor teamColor, SerializableLocation spawnLocation) {
        this.teamColor = teamColor;
        this.spawnLocation = spawnLocation;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public SerializableLocation getSpawnLocation() {
        return spawnLocation;
    }
}
