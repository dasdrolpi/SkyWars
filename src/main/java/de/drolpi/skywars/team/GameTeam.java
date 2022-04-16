package de.drolpi.skywars.team;

public class GameTeam {

    private final String name;
    private final TeamColor color;
    private final TeamPlayers teamPlayers;

    public GameTeam(String name, TeamColor color, int maxPlayerCount) {
        this.name = name;
        this.color = color;
        this.teamPlayers = new TeamPlayers(this, maxPlayerCount);
    }

    public String getName() {
        return name;
    }

    public TeamColor getColor() {
        return color;
    }

    public TeamPlayers getPlayers() {
        return teamPlayers;
    }

    public boolean isAlive() {
        return teamPlayers.count() > 0;
    }

    public boolean isFull() {
        return getPlayers().count() == getPlayers().max();
    }

}
