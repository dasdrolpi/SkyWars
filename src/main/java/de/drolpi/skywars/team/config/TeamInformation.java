package de.drolpi.skywars.team.config;

import de.drolpi.skywars.team.TeamColor;
import lombok.Data;

@Data
public class TeamInformation {

    private final String name;
    private final TeamColor teamColor;
    private final int maxPlayerCount;

    public TeamInformation(String name, TeamColor teamColor, int maxPlayerCount) {
        this.name = name;
        this.teamColor = teamColor;
        this.maxPlayerCount = maxPlayerCount;
    }
}
