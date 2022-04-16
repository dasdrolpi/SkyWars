package de.drolpi.skywars.team.result;

import de.drolpi.skywars.team.GameTeam;

public class TeamFullResult extends AbstractAddResult {

    public TeamFullResult(GameTeam team) {
        super(team);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}