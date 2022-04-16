package de.drolpi.skywars.team.result;

import de.drolpi.skywars.team.GameTeam;

public class AlreadyInTeamResult extends AbstractAddResult {

    public AlreadyInTeamResult(GameTeam team) {
        super(team);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}