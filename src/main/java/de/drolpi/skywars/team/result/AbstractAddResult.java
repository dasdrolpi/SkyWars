package de.drolpi.skywars.team.result;

import de.drolpi.skywars.team.GameTeam;

public abstract class AbstractAddResult {

    private final GameTeam team;

    public AbstractAddResult(GameTeam team) {
        this.team = team;
    }

    public GameTeam getTeam() {
        return team;
    }

    public abstract boolean isSuccess();
}