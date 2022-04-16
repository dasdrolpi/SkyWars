package de.drolpi.skywars.game;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.kit.KitManager;
import de.drolpi.skywars.localization.MessageProvider;
import de.drolpi.skywars.mapvote.MapManager;
import de.drolpi.skywars.phase.PhasenManager;
import de.drolpi.skywars.phase.collective.ending.EndingPhase;
import de.drolpi.skywars.phase.collective.lobby.LobbyPhase;
import de.drolpi.skywars.scoreboard.CustomScoreboard;
import de.drolpi.skywars.scoreboard.ScoreboardHub;
import de.drolpi.skywars.scoreboard.creator.AbstractScoreboardConfiguration;
import de.drolpi.skywars.team.GameTeam;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.util.PlayerUtil;
import de.drolpi.skywars.visibility.PlayerVisibilityRestrictionManager;
import de.drolpi.skywars.visibility.VisibilityRestriction;
import de.drolpi.skywars.world.LocalWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

public class GameManager {

    public static final String TEAM_NAME = "Ghost";
    private Team ghost;

    private final SkyWars skyWars;
    private final Server server;
    private final TeamRepository teamRepository;
    private final PhasenManager phasenManager;
    private final MapManager mapManager;
    private final MessageProvider messageProvider;
    private final VisibilityRestriction visibilityRestriction;
    private final ScoreboardHub scoreboardHub;
    private final KitManager kitManager;

    private final Map<Player, Integer> kills;

    public GameManager(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.server = skyWars.getPlugin().getServer();
        this.teamRepository = skyWars.getTeamRepository();
        this.phasenManager = skyWars.getPhasenManager();
        this.mapManager = skyWars.getMapManager();
        this.messageProvider = skyWars.getMessageProvider();
        this.visibilityRestriction = new PlayerVisibilityRestrictionManager(
                (player, player2) -> !teamRepository.isInTeam(player) && teamRepository.isInTeam(player2)
        );
        this.scoreboardHub = ScoreboardHub.create(skyWars.getPlugin());
        this.kitManager = skyWars.getKitManager();
        this.kills = new WeakHashMap<>();
    }

    public void createTeam() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(TEAM_NAME);

        if(team == null) {
            this.ghost = scoreboard.registerNewTeam(TEAM_NAME);
        }else {
            this.ghost = team;
        }

        this.ghost.setCanSeeFriendlyInvisibles(true);
    }

    public void loadScoreboard() {
        scoreboardHub.getCreator().createScoreboard("scoreboard", new AbstractScoreboardConfiguration() {
            @Override
            public void configure() {
                configureTitleHandler(result -> {
                    Player player = result.getPlayer();

                    if (phasenManager.getCurrentPhase() instanceof LobbyPhase) {
                        return messageProvider.getString(Locale.GERMAN, "skywars.scoreboard_lobby_title");
                    }

                    if(phasenManager.getCurrentPhase() instanceof EndingPhase) {
                        return messageProvider.getString(Locale.GERMAN, "skywars.scoreboard_ending_title");
                    }

                    if (teamRepository.isInTeam(player)) {
                        return messageProvider.getString(Locale.GERMAN, "skywars.scoreboard_ingame_title");
                    }else {
                        return messageProvider.getString(Locale.GERMAN, "skywars.scoreboard_spectator_title");
                    }
                });

                configureLinesHandler(result -> {
                    Player player = result.getPlayer();
                    String mapName = "Voting";
                    LocalWorld world = mapManager.getCurrentWorld();
                    if (world != null) {
                        mapName = world.getName();
                    }

                    if (phasenManager.getCurrentPhase() instanceof LobbyPhase) {
                        return messageProvider.getStringList(
                                Locale.GERMAN,
                                "skywars.scoreboard_lobby_lines",
                                mapName,
                                kitManager.getKitByPlayer(player).getName(),
                                server.getOnlinePlayers().size(),
                                teamRepository.getTeams().stream().mapToInt(value -> value.getPlayers().max()).sum()
                        );
                    }

                    if(phasenManager.getCurrentPhase() instanceof EndingPhase) {
                        return messageProvider.getStringList(
                                Locale.GERMAN,
                                "skywars.scoreboard_ending_lines",
                                mapName,
                                server.getOnlinePlayers().size(),
                                teamRepository.getTeams().stream().mapToInt(value -> value.getPlayers().max()).sum()
                        );
                    }

                    if (teamRepository.isInTeam(player)) {
                        return messageProvider.getStringList(
                                Locale.GERMAN,
                                "skywars.scoreboard_ingame_lines",
                                mapName,
                                kitManager.getKitByPlayer(result.getPlayer()).getName(),
                                teamRepository.getAlivePlayerCount(),
                                teamRepository.getTeams().stream().mapToInt(value -> value.getPlayers().max()).sum(),
                                getKills(player)
                        );
                    } else {
                        return messageProvider.getStringList(
                                Locale.GERMAN,
                                "skywars.scoreboard_spectator_lines",
                                mapName,
                                server.getOnlinePlayers().stream().filter(onlinePlayer -> !teamRepository.isInTeam(onlinePlayer)).count(),
                                teamRepository.getAlivePlayerCount(),
                                teamRepository.getTeams().stream().mapToInt(value -> value.getPlayers().max()).sum()
                        );
                    }
                });
            }
        });
    }

    public void showScoreboard(Player player) {
        scoreboardHub.showScoreboard(player, "scoreboard");
    }

    public void hideScoreboard(Player player) {
        scoreboardHub.hideScoreboard(player);
    }

    public void updateScoreboard() {
        for (Player player : server.getOnlinePlayers()) {
            updateScoreboard(player);
        }
    }

    public void updateScoreboard(Player player) {
        CustomScoreboard scoreboard = scoreboardHub.getScoreboard(player, "scoreboard");
        if(scoreboard == null) return;

        scoreboard.updateTitle();
        scoreboard.update();
    }

    public void addSpectator(Player player) {
        PlayerUtil.resetPlayer(player);
        PlayerUtil.clearPlayer(player);

        player.sendMessage(
                messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                    + messageProvider.getString(Locale.GERMAN, "skywars.ingame.spectator")
        );

        player.teleport(mapManager.getCurrentWorld().getSpectatorSpawn().toLocation(mapManager.getCurrentWorldWrapper().getBukkitWorld().getName()));
        player.playSound(player.getLocation(), Sound.IRONGOLEM_DEATH, 1, 5);
        player.setGameMode(GameMode.ADVENTURE);
        player.spigot().setCollidesWithEntities(false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15, true, false));
        ghost.addPlayer(player);

        PlayerUtil.enableFly(player);

        updateVisibility(player);
    }

    public void removeSpectator(Player player ) {
        ghost.removePlayer(player);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        PlayerUtil.resetPlayer(player);

        updateVisibility(player);
    }
    
    public void resetSpectators() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            removeSpectator(onlinePlayer);
        }
    }

    public void updateVisibility(Player player) {
        visibilityRestriction.handleUpdate(player, Bukkit.getOnlinePlayers());
        visibilityRestriction.handleUpdate(Bukkit.getOnlinePlayers(), player);
    }

    public void removePlayer(Player player, RemoveReason removeReason) {
        Optional<GameTeam> optional = teamRepository.getTeamByPlayer(player);

        if(!optional.isPresent()) {
            return;
        }

        GameTeam team = optional.get();
        team.getPlayers().remove(player);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                            + messageProvider.getString(Locale.GERMAN, "skywars.ingame.player_remaining_brodcast", teamRepository.getAlivePlayerCount())
            );
        }

        player.sendTitle(messageProvider.getString(Locale.GERMAN, "skywars.ingame.death_title"), "");

        checkWin();

        if(removeReason.equals(RemoveReason.DEATH) && !isWin()) {
            addSpectator(player);
        }
    }

    public Optional<GameTeam> getWinner() {
        if(isWin()) {
            return teamRepository.getAliveTeams().stream().findFirst();
        }

        return Optional.empty();
    }

    public void checkWin() {
        if(isWin()) {
            Optional<GameTeam> optional = getWinner();

            Collection<? extends Player> players = Bukkit.getOnlinePlayers();

            String teamName = "§c???";
            String teamPlayers = "§c???";

            if(optional.isPresent()) {
               GameTeam team = optional.get();


                teamName = team.getColor().getChatColor() + team.getName();
                teamPlayers = team.getColor().getChatColor() + team.getPlayers().collect().stream().map(HumanEntity::getName).collect(Collectors.joining(","));

                for (Player player : players) {
                    if(team.getPlayers().isTeamMate(player)) {
                        team.getPlayers().remove(player);
                    }
                }
            }

            for (Player player : players) {
                player.sendTitle(
                        messageProvider.getString(Locale.GERMAN, "skywars.ending.win_title"),
                        messageProvider.getString(Locale.GERMAN, "skywars.ending.win_subtitle", teamName)
                );
                String prefix = messageProvider.getString(Locale.GERMAN, "skywars.prefix");

                player.sendMessage(messageProvider.getString(Locale.GERMAN, "skywars.ending.win_brodcast", teamName, teamPlayers).replaceAll("%prefix%", prefix));
            }
            phasenManager.setPhase(new EndingPhase(skyWars));
        }
    }

    private boolean isWin() {
        return teamRepository.getAliveTeams().size() <= 1;
    }

    public void addKill(Player player) {
        if(!kills.containsKey(player)) {
            kills.put(player, 0);
        }

        kills.put(player, kills.get(player) + 1);
    }

    public int getKills(Player player) {
        if(!kills.containsKey(player)) {
            kills.put(player, 0);
        }

        return kills.get(player);
    }

    public enum RemoveReason {

        QUIT,
        DEATH;

    }

}
