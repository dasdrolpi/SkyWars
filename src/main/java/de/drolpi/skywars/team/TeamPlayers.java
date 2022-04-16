package de.drolpi.skywars.team;

import de.drolpi.skywars.team.result.AbstractAddResult;
import de.drolpi.skywars.team.result.AlreadyInTeamResult;
import de.drolpi.skywars.team.result.SuccessfulAddResult;
import de.drolpi.skywars.team.result.TeamFullResult;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TeamPlayers {

    private final Set<Player> players;
    private final GameTeam team;
    private final int maxPlayerCount;

    public TeamPlayers(GameTeam team, int maxPlayerCount) {
        this.team = team;
        this.maxPlayerCount = maxPlayerCount;
        this.players = new HashSet<>();
    }

    public GameTeam getTeam() {
        return team;
    }

    public Set<Player> collect() {
        return Collections.unmodifiableSet(players);
    }

    public int count() {
        return players.size();
    }

    public int max() {
        return maxPlayerCount;
    }

    public boolean isTeamMate(Player other) {
        return team != null && collect().contains(other);
    }

    public AbstractAddResult add(Player player) {
        if (team.isFull()) {
            return new TeamFullResult(team);
        }

        if (!this.players.add(player)) {
            return new SuccessfulAddResult(team);
        }

        return new AlreadyInTeamResult(team);
    }

    public void remove(Player player) {
        this.players.remove(player);
    }
}