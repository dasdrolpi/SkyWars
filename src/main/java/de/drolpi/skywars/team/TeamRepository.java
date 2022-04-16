package de.drolpi.skywars.team;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.localization.MessageProvider;
import de.drolpi.skywars.team.config.TeamConfig;
import de.drolpi.skywars.team.config.TeamConfigLoader;
import de.drolpi.skywars.team.config.TeamInformation;
import de.drolpi.skywars.team.result.AbstractAddResult;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamRepository {

    private final Map<String, GameTeam> teams;
    private final MessageProvider messageProvider;
    private final TeamConfigLoader configLoader;

    public TeamRepository(SkyWars skyWars) {
        this.messageProvider = skyWars.getMessageProvider();
        this.teams = new HashMap<>();
        this.configLoader = new TeamConfigLoader();
    }

    public void loadTeams() {
        TeamConfig teamConfig = configLoader.loadOrCreateFile();

        for (TeamInformation team : teamConfig.getTeams()) {
            teams.put(team.getName(), new GameTeam(team.getName(), team.getTeamColor(), team.getMaxPlayerCount()));
        }
    }

    public Optional<GameTeam> getTeamByName(String name) {
        return Optional.ofNullable(teams.get(name));
    }

    public Optional<GameTeam> getTeamByPlayer(Player player) {
        return teams.values().stream().filter(team -> team.getPlayers().isTeamMate(player)).findFirst();
    }

    public GameTeam getMostFullAndFreeTeam() {
        return teams
                .values()
                .stream()
                .filter(gameTeam -> !gameTeam.isFull())
                .max(Comparator.comparingInt(value -> value.getPlayers().count()))
                .orElseThrow(() -> new IllegalStateException("No teams present"));
    }

    public Set<GameTeam> getTeams() {
        return Collections.unmodifiableSet(new HashSet<>(teams.values()));
    }

    public Set<GameTeam> getAliveTeams() {
        return getTeams().stream().filter(GameTeam::isAlive).collect(Collectors.toSet());
    }

    public int getAliveTeamCount() {
        return getAliveTeams().size();
    }

    public int getAlivePlayerCount() {
        return getAliveTeams().stream().mapToInt(value -> value.getPlayers().count()).sum();
    }

    public void fillTeams(Collection<? extends Player> players) {
        for (Player player : players) {
            if(!isInTeam(player)) {
                GameTeam team = getMostFullAndFreeTeam();
                team.getPlayers().add(player);
                player.sendMessage(
                        messageProvider.getString(Locale.GERMAN, "skywars.prefix") +
                                messageProvider.getString(Locale.GERMAN, "skywars.team_join", team.getColor().getChatColor() + team.getName())
                );
            }
        }
    }

    public boolean isInTeam(Player player) {
        for (GameTeam team : getTeams()) {
            if(team.getPlayers().isTeamMate(player)) {
                return true;
            }
        }
        return false;
    }
}