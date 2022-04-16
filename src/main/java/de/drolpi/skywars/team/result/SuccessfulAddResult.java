package de.drolpi.skywars.team.result;

import de.drolpi.skywars.team.GameTeam;

public class SuccessfulAddResult extends AbstractAddResult {

    public SuccessfulAddResult(GameTeam team) {
        super(team);
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}