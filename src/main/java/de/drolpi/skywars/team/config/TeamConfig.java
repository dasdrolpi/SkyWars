package de.drolpi.skywars.team.config;

import de.drolpi.skywars.team.TeamColor;

import java.util.HashSet;
import java.util.Set;

public class TeamConfig {

    private final Set<TeamInformation> teams;

    public TeamConfig() {
        this.teams = new HashSet<>();

        teams.add(new TeamInformation("Red", TeamColor.RED, 1));
        teams.add(new TeamInformation("Blue", TeamColor.BLUE, 1));
        teams.add(new TeamInformation("Green", TeamColor.GREEN, 1));
        teams.add(new TeamInformation("Yellow", TeamColor.YELLOW, 1));
        teams.add(new TeamInformation("Pink", TeamColor.PINK, 1));
        teams.add(new TeamInformation("Lightblue", TeamColor.LIGHT_BLUE, 1));
        teams.add(new TeamInformation("Lightgreen", TeamColor.LIGHT_GREEN, 1));
        teams.add(new TeamInformation("Orange", TeamColor.ORANGE, 1));
    }

    public Set<TeamInformation> getTeams() {
        return teams;
    }
}
